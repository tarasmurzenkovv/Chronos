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
import java.util.List;

@Service
public class CsvViewRenderer implements ViewRenderer {
    private static final int ERROR_CODE = 16;
    private static final String[] HEADERS = new String[]{"Project name", "First name", "Last name", "Job title",
            "Spent time", "Reporting date(YYYY-MM-DD)" , "Comments"};

    @Override
    public ReportingResponse writeToFile(List<Report> reports, Range range) {
        String fileName = String.format("time_report_%s_%s.csv",
                DateTimeUtils.format(range.getStart(), "dd_MM_yyyy"),
                DateTimeUtils.format(range.getEnd(), "dd_MM_yyyy"));

        try (StringWriter writer = new StringWriter();
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            reports.forEach(report -> CsvViewRenderer.printRecord(report, printer));
            return new ReportingResponse(fileName, writer.toString().getBytes());
        } catch (IOException e) {
            throw new ReportingException(ERROR_CODE, e.getMessage());
        }
    }

    @SneakyThrows
    private static void printRecord(Report report, CSVPrinter csvPrinter) {
        csvPrinter.printRecord(report.getProjectName(), report.getFirstName(), report.getLastName(),
                report.getJobTitle(), report.getSpentTime(), DateTimeUtils.format(report.getReportingDate(), "YYYY-MM-DD"),
                report.getComments());
    }
}
