package com.syngenta.digital.lab.kyiv.chronos.service.report.view;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Range;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingResponse;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ReportingException;
import com.syngenta.digital.lab.kyiv.chronos.utils.DateTimeUtils;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CsvViewRenderer implements ViewRenderer {
    private static final int ERROR_CODE = 16;

    @Override
    public ReportingResponse writeToFile(List<Report> reports, Range range) {
        String fileName = getFileName();

        try (StringWriter writer = new StringWriter();
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            printer.printRecord("Generation Date", DateTimeUtils.format(LocalDateTime.now(), "dd/MM/yyyy HH:mm"));
            printer.printRecord("Start date:", DateTimeUtils.format(range.getStart(), "dd/MM/yyyy"));
            printer.printRecord("End date:", DateTimeUtils.format(range.getEnd(), "dd/MM/yyyy"));
            printer.printRecord("Project name", "First name", "Last name", "Job title",
                    "Spent time", "Reporting date (DD.MM.YYYY)" , "Comments");
            reports.forEach(report -> CsvViewRenderer.printRecord(report, printer));
            return new ReportingResponse(fileName, writer.toString().getBytes());
        } catch (IOException e) {
            throw new ReportingException(ERROR_CODE, e.getMessage());
        }
    }

    private String getFileName() {
        return String.format("time_report_%s.csv", DateTimeUtils.format(LocalDate.now(), "dd_MM_YYYY"));
    }

    @SneakyThrows
    private static void printRecord(Report report, CSVPrinter csvPrinter) {
        csvPrinter.printRecord(report.getProjectName(), report.getFirstName(), report.getLastName(),
                report.getJobTitle(), report.getSpentTime(), DateTimeUtils.format(report.getReportingDate(), "dd/MMYYYY"),
                report.getComments());
    }
}
