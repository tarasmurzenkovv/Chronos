package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ResetPasswordRequest;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.ExpectedGeneratedQueryNumber;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.ExpectedGeneratedQueryNumbers;
import com.syngenta.digital.lab.kyiv.chronos.utils.db.utils.QueryType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@DatabaseTearDown("/dbTearDown.xml")
public class UserControllerIntegrationTest extends BaseIntegrationTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/UserControllerIntegrationTest/shouldUpdateUserPassword/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldUpdateUserPassword/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumbers({
            @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2),
            @ExpectedGeneratedQueryNumber(queryType = QueryType.UPDATE, expectedNumber = 1)
    })
    public void shouldUpdateUserPassword() {
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("qwerty100$");
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldUpdateUserPassword/resetPasswordRequest.json", ResetPasswordRequest.class))
                .put("/api/v0/user/password")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/UserControllerIntegrationTest/shouldFindAllTasksForUserIdAndTimePeriod/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFindAllTasksForUserIdAndTimePeriod/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFindAllTasksForUserIdAndTimePeriod() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .param("start", "01/01/2019")
                .param("end", "01/02/2019")
                .get("/api/v0/user/1")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<List<TaskDto>> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<List<TaskDto>>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotEmpty();

        Assertions.assertThat(actualResponse.getData().get(0).getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(0).getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(0).getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(0).getComments()).isEqualTo("comments");
        Assertions.assertThat(actualResponse.getData().get(0).getSpentTime()).isEqualTo(0.8f);

        Assertions.assertThat(actualResponse.getData().get(1).getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(1).getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(1).getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(1).getComments()).isEqualTo("comments");
        Assertions.assertThat(actualResponse.getData().get(1).getSpentTime()).isEqualTo(0.8f);

        List<LocalDate> reportingDates = actualResponse.getData()
                .stream()
                .map(TaskDto::getReportingDate)
                .collect(Collectors.toList());

        Assertions.assertThat(reportingDates).isNotEmpty();
        Assertions.assertThat(reportingDates)
                .isEqualTo(List.of(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 2, 1)));
    }


    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/UserControllerIntegrationTest/shouldGetTasksByUserId/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldGetTasksByUserId/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldGetTasksByUserId() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .get("/api/v0/user/1/task")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<List<TaskDto>> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<List<TaskDto>>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotEmpty();

        Assertions.assertThat(actualResponse.getData().get(0).getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(0).getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(0).getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(0).getComments()).isEqualTo("comments");
        Assertions.assertThat(actualResponse.getData().get(0).getReportingDate()).isEqualTo(LocalDate.of(2019, 1, 1));
        Assertions.assertThat(actualResponse.getData().get(0).getSpentTime()).isEqualTo(0.8f);

        Assertions.assertThat(actualResponse.getData().get(1).getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(1).getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(1).getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().get(1).getComments()).isEqualTo("comments");
        Assertions.assertThat(actualResponse.getData().get(1).getReportingDate()).isEqualTo(LocalDate.of(2019, 1, 1));
        Assertions.assertThat(actualResponse.getData().get(1).getSpentTime()).isEqualTo(0.8f);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 1)
    public void shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest/loginRequest.json", LoginRequest.class))
                .post("/api/v0/user/login")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });
        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoSuchEmailIsPresentInDb/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoSuchEmailIsPresentInDb/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailLoginTheExistingUserIfNoSuchEmailIsPresentInDb() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoSuchEmailIsPresentInDb/loginRequest.json", LoginRequest.class))
                .post("/api/v0/user/login")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });
        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoSuchEmailIsPresentInDb/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldSuccessfullyLoginTheExistingUser/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldSuccessfullyLoginTheExistingUser/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldSuccessfullyLoginTheExistingUser() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldSuccessfullyLoginTheExistingUser/loginRequest.json", LoginRequest.class))
                .post("/api/v0/user/login")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<UserDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<UserDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(actualResponse.getData().getFirstName()).isEqualTo("First_name");
        Assertions.assertThat(actualResponse.getData().getLastName()).isEqualTo("Last_name");
        Assertions.assertThat(actualResponse.getData().getPassword()).isNull();
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldSuccessfullyLoginTheExistingUser/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldSuccessfullyRegisterNewUser/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumbers({
            @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 3),
            @ExpectedGeneratedQueryNumber(queryType = QueryType.INSERT, expectedNumber = 1)
    })
    public void shouldSuccessfullyRegisterNewUser() {
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("passW3$rdd");

        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldSuccessfullyRegisterNewUser/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<UserDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<UserDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getEmail()).isEqualTo("email@emial.com");
        Assertions.assertThat(actualResponse.getData().getFirstName()).isEqualTo("First_name");
        Assertions.assertThat(actualResponse.getData().getLastName()).isEqualTo("Last_name");
        Assertions.assertThat(actualResponse.getData().getPassword()).isNull();
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNotUnique/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNotUnique/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfEmailIsNotUnique() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNotUnique/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });
        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNotUnique/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }


    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsNull/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsNull/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfFirstNameIsNull() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsNull/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });
        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsNull/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsEmpty/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsEmpty/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfFirstNameIsEmpty() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsEmpty/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsEmpty/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsNull/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsNull/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfLastNameIsNull() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsNull/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });
        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsNull/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsEmpty/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsEmpty/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfLastNameIsEmpty() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsEmpty/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });
        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsEmpty/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailHasInvalidFormat/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailHasInvalidFormat/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfEmailHasInvalidFormat() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailHasInvalidFormat/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);
        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(),
                new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
                });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailHasInvalidFormat/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsBlank/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsBlank/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfEmailIsBlank() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsBlank/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });


        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsBlank/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNull/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNull/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfEmailIsNull() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNull/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });


        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNull/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordHasInvalidFormat/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordHasInvalidFormat/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfPasswordHasInvalidFormat() {
        Response response = this.getRestAssured()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordHasInvalidFormat/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });


        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordHasInvalidFormat/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordIsNull/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordIsNull/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    @ExpectedGeneratedQueryNumber(queryType = QueryType.SELECT, expectedNumber = 2)
    public void shouldFailToRegisterNewUserIfPasswordIsNull() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordIsNull/registrationRequest.json", UserDto.class))
                .post("/api/v0/user")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });


        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordIsNull/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
