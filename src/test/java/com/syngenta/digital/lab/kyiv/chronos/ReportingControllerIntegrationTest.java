package com.syngenta.digital.lab.kyiv.chronos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.utils.FileUtils;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@DatabaseTearDown("/dbTearDown.xml")
public class ReportingControllerIntegrationTest extends BaseIntegrationTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    @SneakyThrows
    @DatabaseSetups({
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateTheCsvReport/dbSetup/projectType.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateTheCsvReport/dbSetup/project.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateTheCsvReport/dbSetup/users.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateTheCsvReport/dbSetup/tasks.xml")
    })
    @ExpectedDatabase(value = "/ReportingControllerIntegrationTest/shouldGenerateTheCsvReport/dbSetup/expectedTasks.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldGenerateTheCsvReport() {
        Response response = RestAssured
                .given()
                .get("/api/v0/reporting/csv?id=1&id=2&id=3&start=01/01/2016&end=01/01/2020")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getHeader("Content-Disposition"))
                .isEqualTo("attachment; filename=time_report_01_01_2016_01_01_2020.csv");
        byte[] bytes = response.getBody().asByteArray();
        Path testReportingFilePath = testFolder.newFile("time_report_01_01_2016_01_01_2020.csv").toPath();
        BufferedReader reader = Files.newBufferedReader(Files.write(testReportingFilePath, bytes));
        List<String> actualParsedCsvFile = reader.lines().collect(Collectors.toList());
        List<String> expectedParsedCsvFile = FileUtils.parseCsvFile("/ReportingControllerIntegrationTest/shouldGenerateTheCsvReport/response/expectedCsvFile.csv");
        Assertions.assertThat(actualParsedCsvFile).containsExactlyInAnyOrderElementsOf(expectedParsedCsvFile);
    }

    @Test
    @SneakyThrows
    public void shouldNotGenerateTheReportIfStartDateIsAfterBeforeDate() {
        Response response = RestAssured
                .given()
                .get("/api/v0/reporting/csv?id=1&id=2&id=3&start=01/01/2021&end=01/01/2020")
                .then()
                .extract()
                .response();

        validateBadResponse(response);

        GeneralResponse<ErrorResponsePayload> actualResponse = this.objectMapper.readValue(response.getBody().asString(), new TypeReference<GeneralResponse<ErrorResponsePayload>>() {
        });


        GeneralResponse<ErrorResponsePayload> expectedResponse = JsonUtils.readFromJson(
                "/ReportingControllerIntegrationTest/shouldNotGenerateTheReportIfStartDateIsAfterBeforeDate/response/expectedResponse.json",
                new TypeReference<>() {
                });

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
