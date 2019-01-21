package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@DatabaseTearDown("/dbTearDown.xml")
public class TaskControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldNotEditFrozenTask/dbSetup.xml")
    public void shouldNotEditFrozenTask() {

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/TaskControllerIntegrationTest/shouldNotEditFrozenTask/updateTheExistingTaskRequest.json", TaskDto.class))
                .put("/api/v0/task")
                .then()
                .extract()
                .response();

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });


        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/TaskControllerIntegrationTest/shouldNotEditFrozenTask/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldFindTaskTags/dbSetup.xml")
    public void shouldFindTaskTags() {

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .get("/api/v0/task/1/tags")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<List<TagDto>> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<List<TagDto>>>() {
        });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().stream().map(TagDto::getTag).collect(Collectors.toList()))
                .containsExactlyInAnyOrderElementsOf(List.of("#awesome_tag_existing", "#awesome_new_new_tag", "#awesome_tag"));
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldAddNewTaskWithNewTags/dbSetup.xml")
    public void shouldAddNewTaskWithNewTags() {

        Response response = RestAssured
                .given()
                .body(JsonUtils.readFromJson("/TaskControllerIntegrationTest/shouldAddNewTaskWithNewTags/addNewTaskWithNewTagsRequest.json", TaskDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/task")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<TaskDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<TaskDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getComments()).isEqualTo("comments");
        Assertions.assertThat(actualResponse.getData().getReportingDate()).isEqualTo(LocalDate.of(2019,1,1));
        Assertions.assertThat(actualResponse.getData().getSpentTime()).isEqualTo(0.8f);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldAddNewTask/dbSetup.xml")
    @ExpectedDatabase(value = "/TaskControllerIntegrationTest/shouldAddNewTask/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldAddNewTask() {

        Response response = RestAssured
                .given()
                .body(JsonUtils.readFromJson("/TaskControllerIntegrationTest/shouldAddNewTask/addNewTaskRequest.json", TaskDto.class))
                .contentType(ContentType.JSON)
                .post("/api/v0/task")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<TaskDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<TaskDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getComments()).isEqualTo("comments");
        Assertions.assertThat(actualResponse.getData().getReportingDate()).isEqualTo(LocalDate.of(2019,1,1));
        Assertions.assertThat(actualResponse.getData().getSpentTime()).isEqualTo(0.8f);
        Assertions.assertThat(actualResponse.getData().getTags()).isEqualTo(Collections.emptyList());
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldGetTaskByItsId/dbSetup.xml")
    @ExpectedDatabase(value = "/TaskControllerIntegrationTest/shouldGetTaskByItsId/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldGetTaskByItsId() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .get("/api/v0/task/1")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<TaskDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<TaskDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getComments()).isEqualTo("comments");
        Assertions.assertThat(actualResponse.getData().getReportingDate()).isEqualTo(LocalDate.of(2019,1,1));
        Assertions.assertThat(actualResponse.getData().getSpentTime()).isEqualTo(0.8f);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldUpdateTheExistingTaskWithNewAndExistingTags/dbSetup.xml")
    public void shouldUpdateTheExistingTaskWithNewAndExistingTags() {

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/TaskControllerIntegrationTest/shouldUpdateTheExistingTaskWithNewAndExistingTags/updateTheExistingTaskWithNewAndExistingTags.json", TaskDto.class))
                .put("/api/v0/task")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<TaskDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<TaskDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getComments()).isEqualTo("new comments");
        Assertions.assertThat(actualResponse.getData().getReportingDate()).isEqualTo(LocalDate.of(2019,1,1));
        Assertions.assertThat(actualResponse.getData().getSpentTime()).isEqualTo(0.8f);
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldUpdateTheExistingTask/dbSetup.xml")
    @ExpectedDatabase(value = "/TaskControllerIntegrationTest/shouldUpdateTheExistingTask/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldUpdateTheExistingTask() {

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.readFromJson("/TaskControllerIntegrationTest/shouldUpdateTheExistingTask/updateTheExistingTaskRequest.json", TaskDto.class))
                .put("/api/v0/task")
                .then()
                .extract()
                .response();

        String asGeneralResponseString = response.getBody().asString();
        GeneralResponse<TaskDto> actualResponse = this.objectMapper.readValue(asGeneralResponseString, new TypeReference<GeneralResponse<TaskDto>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(302);
        Assertions.assertThat(actualResponse).isNotNull();
        Assertions.assertThat(actualResponse.getData()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getTaskId()).isNotNull();
        Assertions.assertThat(actualResponse.getData().getProjectId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getUserId()).isEqualTo(1);
        Assertions.assertThat(actualResponse.getData().getComments()).isEqualTo("new comments");
        Assertions.assertThat(actualResponse.getData().getReportingDate()).isEqualTo(LocalDate.of(2019,1,1));
        Assertions.assertThat(actualResponse.getData().getSpentTime()).isEqualTo(0.8f);
        Assertions.assertThat(actualResponse.getData().getTags()).isEqualTo(Collections.emptyList());
    }

    @Test
    @SneakyThrows
    @DatabaseSetup(value = "/TaskControllerIntegrationTest/shouldNotEditFrozenTask/dbSetup.xml")
    @ExpectedDatabase(value = "/TaskControllerIntegrationTest/shouldNotEditFrozenTask/expectedDataBase.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldDeleteTheExistingTask() {

        Response response = RestAssured
                .given()
                .delete("/api/v0/task/1")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}
