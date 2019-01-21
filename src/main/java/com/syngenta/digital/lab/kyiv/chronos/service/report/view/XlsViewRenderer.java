package com.syngenta.digital.lab.kyiv.chronos.service.report.view;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Range;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingResponse;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ReportingException;
import com.syngenta.digital.lab.kyiv.chronos.utils.DateTimeUtils;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class XlsViewRenderer implements ViewRenderer {
    private static final int ERROR_CODE = 16;

    @Override
    public synchronized ReportingResponse writeToFile(List<Report> reports, Range range) {
        int rowIndex = 0;
        Workbook workbook = new XSSFWorkbook();

        String fileName = String.format("time_report_%s.xlsx",
                DateTimeUtils.format(LocalDate.now(), "dd_MM_YYYY"));
        Sheet timeReporting = workbook.createSheet("time_reporting");

        Row generationDateRow = timeReporting.createRow(rowIndex++);
        generationDateRow.createCell(0)
                .setCellValue("Generation Date:");
        generationDateRow.createCell(1).setCellValue(DateTimeUtils.format(LocalDateTime.now(), "dd/MM/yyyy HH:mm"));

        Row startDateRow = timeReporting.createRow(rowIndex++);
        startDateRow.createCell(0)
                .setCellValue("Start date:");
        startDateRow.createCell(1).setCellValue(DateTimeUtils.format(range.getStart(), "dd/MM/yyyy"));

        Row endDateRow = timeReporting.createRow(rowIndex++);
        endDateRow.createCell(0)
                .setCellValue("End date:");
        endDateRow.createCell(1).setCellValue(DateTimeUtils.format(range.getEnd(), "dd/MM/yyyy"));

        Row headerRow = timeReporting.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Project name");
        headerRow.createCell(1).setCellValue("First name");
        headerRow.createCell(2).setCellValue("Last name");
        headerRow.createCell(3).setCellValue("Job Title");
        headerRow.createCell(4).setCellValue("Spent time");
        headerRow.createCell(5).setCellValue("Reporting date (dd.MM.YYYY)");
        headerRow.createCell(6).setCellValue("Comments");

        for (final Report report : reports) {
            Row row = timeReporting.createRow(rowIndex++);
            row.createCell(0).setCellValue(report.getProjectName());
            row.createCell(1).setCellValue(report.getFirstName());
            row.createCell(2).setCellValue(report.getLastName());
            row.createCell(3).setCellValue(report.getJobTitle());
            row.createCell(4).setCellValue(report.getSpentTime());
            row.createCell(5).setCellValue(DateTimeUtils.format(report.getReportingDate(), "dd/MM/YYYY"));
            row.createCell(6).setCellValue(report.getComments());
        }
        timeReporting.autoSizeColumn(0);
        timeReporting.autoSizeColumn(1);
        timeReporting.autoSizeColumn(2);
        timeReporting.autoSizeColumn(3);
        timeReporting.autoSizeColumn(4);
        timeReporting.autoSizeColumn(5);
        timeReporting.autoSizeColumn(6);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            workbook.close();
            return new ReportingResponse(fileName, byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new ReportingException(ERROR_CODE, e.getMessage());
        }
    }
}
