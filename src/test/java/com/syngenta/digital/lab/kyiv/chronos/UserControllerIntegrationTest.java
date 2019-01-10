package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChronosApplicationEntryPoint.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DatabaseTearDown("/UserControllerIntegrationTest/dbTearDown.xml")
public class UserControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest/dbSetup.xml")
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldFailLoginTheExistingUserIfNoPasswordIsPresentInRequest() {
        Response response = this.getPreConfiguredRestAssured()
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
    public void shouldFailLoginTheExistingUserIfNoSuchEmailIsPresentInDb() {
        Response response = this.getPreConfiguredRestAssured()
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
    public void shouldSuccessfullyLoginTheExistingUser() {
        Response response = this.getPreConfiguredRestAssured()
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
        Assertions.assertThat(actualResponse.getData().getPassword()).isEqualTo("passW3$rdd");
    }

    @Test
    @SneakyThrows
    @ExpectedDatabase(value = "/UserControllerIntegrationTest/shouldSuccessfullyRegisterNewUser/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldSuccessfullyRegisterNewUser() {
        Response response = this.getPreConfiguredRestAssured()
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
        Assertions.assertThat(actualResponse.getData().getPassword()).isEqualTo("passW3$rdd");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNotUnique/dbSetup.xml")
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNotUnique/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfEmailIsNotUnique() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsNull/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfFirstNameIsNull() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfFirstNameIsEmpty/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfFirstNameIsEmpty() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsNull/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfLastNameIsNull() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfLastNameIsEmpty/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfLastNameIsEmpty() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailHasInvalidFormat/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfEmailHasInvalidFormat() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsBlank/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfEmailIsBlank() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfEmailIsNull/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfEmailIsNull() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordHasInvalidFormat/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfPasswordHasInvalidFormat() {
        Response response = this.getPreConfiguredRestAssured()
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
    @ExpectedDatabase("/UserControllerIntegrationTest/shouldFailToRegisterNewUserIfPasswordIsNull/expectedDataBase.xml")
    public void shouldFailToRegisterNewUserIfPasswordIsNull() {
        Response response = this.getPreConfiguredRestAssured()
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

    private static void validateBadResponse(Response response) {
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
        ResponseBody body = response.getBody();
        Assertions.assertThat(body).isNotNull();
        String asGeneralResponseString = body.asString();
        Assertions.assertThat(asGeneralResponseString).isNotNull();
    }
}
