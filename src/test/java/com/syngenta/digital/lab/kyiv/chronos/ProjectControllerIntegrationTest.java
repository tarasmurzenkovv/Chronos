package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@DatabaseTearDown("/ProjectControllerIntegrationTest/dbTearDown.xml")
public class ProjectControllerIntegrationTest extends BaseIntegrationTest {
    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyAddNewProject/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyAddNewProject/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldSuccessfullyAddNewProject() {
        Response response = RestAssured
                .given()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldSuccessfullyAddNewProject/addNewProjectRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/project")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<ProjectDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<ProjectDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified() {
        Response response = RestAssured
                .given()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/failToAddNewProjectRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/project")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });

        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/ProjectControllerIntegrationTest/shouldFailToAddNewProjectIfNoExistingProjectTypeIdIsSpecified/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindProjectByItsId/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindProjectByItsId/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldSuccessfullyFindProjectByItsId() {
        Response response =RestAssured.given()
                .get("/api/v0/project/1")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<ProjectDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<ProjectDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindAllProjects/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyFindAllProjects/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldSuccessfullyFindAllProjects() {
        Response response = RestAssured.given()
                .get("/api/v0/project")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<List<ProjectDto>> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<List<ProjectDto>>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);

        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotEmpty();
        Assertions.assertThat(actualResponse.getData().size()).isEqualTo(2);

        Assertions.assertThat(actualResponse.getData().get(0).getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(0).getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(0).getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().get(0).getProjectDescription()).isEqualTo("Some useful description");
        Assertions.assertThat(actualResponse.getData().get(1).getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(1).getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().get(1).getProjectName()).isEqualTo("Project name");
        Assertions.assertThat(actualResponse.getData().get(1).getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyUpdateTheExistingProject/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyUpdateTheExistingProject/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldSuccessfullyUpdateTheExistingProject() {
        Response response = RestAssured.given()
                .body(JsonUtils.readFromJson("/ProjectControllerIntegrationTest/shouldSuccessfullyUpdateTheExistingProject/updateTheExistingProjectRequest.json", ProjectDto.class))
                .contentType(ContentType.JSON)
                .put("/api/v0/project")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<ProjectDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<ProjectDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectTypeId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectName()).isEqualTo("Updated project name");
        Assertions.assertThat(actualResponse.getData().getProjectDescription()).isEqualTo("Some useful description");
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyDeleteProjectByItsId/dbSetup.xml")
    @ExpectedDatabase(value = "/ProjectControllerIntegrationTest/shouldSuccessfullyDeleteProjectByItsId/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldSuccessfullyDeleteProjectByItsId() {
        Response response = RestAssured.given()
                .delete("/api/v0/project/1")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
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