package com.syngenta.digital.lab.kyiv.chronos.service.report.view;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Range;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingResponse;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ReportingException;
import com.syngenta.digital.lab.kyiv.chronos.utils.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class CsvViewRenderer implements ViewRenderer {
    private static final int ERROR_CODE = 16;

    @Override
    public ReportingResponse writeToFile(List<Report> reports, Range range) {
        String fileName = String.format("time_report_%s_%s.csv",
                DateTimeUtils.format(range.getStart(), "dd_MM_yyyy"),
                DateTimeUtils.format(range.getEnd(), "dd_MM_yyyy"));

        try (StringWriter writer = new StringWriter()) {
            StatefulBeanToCsv<Report> beanToCsv = new StatefulBeanToCsvBuilder<Report>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(reports);
            return new ReportingResponse(fileName, writer.toString().getBytes());
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new ReportingException(ERROR_CODE, e.getMessage());
        }
    }
}
