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
import com.syngenta.digital.lab.kyiv.chronos.service.ClockService;
import com.syngenta.digital.lab.kyiv.chronos.utils.FileUtils;
import com.syngenta.digital.lab.kyiv.chronos.utils.JsonUtils;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;

@DatabaseTearDown("/dbTearDown.xml")
public class ReportingControllerIntegrationTest extends BaseIntegrationTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @MockBean
    private ClockService clockService;

    @Before
    public void setup() {
        Mockito.when(clockService.nowTime()).thenReturn(LocalDateTime.of(2017, 1, 1, 1, 1));
        Mockito.when(clockService.now()).thenReturn(LocalDate.of(2017, 1, 1));
    }

    @Test
    @SneakyThrows
    @DatabaseSetups({
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/projectType.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/project.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/users.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/tasks.xml")
    })
    @ExpectedDatabase(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/expectedTasks.xml",
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
                .isEqualTo("attachment; filename=time_report_01_01_2017.csv");
        byte[] bytes = response.getBody().asByteArray();
        Path testReportingFilePath = testFolder.newFile("time_report_01_01_2017.csv").toPath();
        BufferedReader reader = Files.newBufferedReader(Files.write(testReportingFilePath, bytes));
        List<String> actualParsedCsvFile = reader.lines().collect(Collectors.toList());
        List<String> expectedParsedCsvFile = FileUtils.parseCsvFile("/ReportingControllerIntegrationTest/shouldGenerateReport/response/expectedCsvFile.csv");
        Assertions.assertThat(actualParsedCsvFile).containsExactlyInAnyOrderElementsOf(expectedParsedCsvFile);
    }

    @Test
    @SneakyThrows
    @DatabaseSetups({
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/projectType.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/project.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/users.xml"),
            @DatabaseSetup(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/tasks.xml")
    })
    @ExpectedDatabase(value = "/ReportingControllerIntegrationTest/shouldGenerateReport/dbSetup/expectedTasks.xml",
            assertionMode = NON_STRICT_UNORDERED)
    public void shouldGenerateTheXlsReport() {
        Response response = RestAssured
                .given()
                .get("/api/v0/reporting/xls?id=1&id=2&id=3&start=01/01/2016&end=01/01/2020")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getHeader("Content-Disposition"))
                .isEqualTo("attachment; filename=time_report_01_01_2017.xlsx");
        byte[] bytes = response.getBody().asByteArray();
        Workbook actualWorkBook = FileUtils.readXlsxFile(bytes);
        Assertions.assertThat(actualWorkBook.getNumberOfSheets()).isEqualTo(1);
        Sheet reportingSheet = actualWorkBook.getSheetAt(0);
        Assertions.assertThat(reportingSheet.getSheetName()).isEqualTo("time_reporting");
        Iterable<Row> iterable = reportingSheet::rowIterator;
        List<String> actualParsedValues = StreamSupport.stream(iterable.spliterator(), false)
                .map(row -> {
                    Iterable<Cell> iterableCells = row::cellIterator;
                    return StreamSupport.stream(iterableCells.spliterator(), false)
                            .map(cell -> {
                                CellType cellType = cell.getCellType();
                                if (cellType == CellType.NUMERIC) {
                                    DecimalFormat decimalFormat = new DecimalFormat("0.#");
                                    return decimalFormat.format(cell.getNumericCellValue());
                                }
                                return cell.getStringCellValue();
                            })
                            .collect(Collectors.joining(","));
                })
                .collect(Collectors.toList());

        List<String> expectedParsedCsvFile = FileUtils.parseCsvFile("/ReportingControllerIntegrationTest/shouldGenerateReport/response/expectedCsvFile.csv");
        Assertions.assertThat(actualParsedValues).containsExactlyInAnyOrderElementsOf(expectedParsedCsvFile);
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
