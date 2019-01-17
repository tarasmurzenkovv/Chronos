package com.syngenta.digital.lab.kyiv.chronos.service.report;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportType;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingResponse;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ReportingException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import com.syngenta.digital.lab.kyiv.chronos.service.report.view.ViewRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingService {
    private static final int ERROR_CODE = 17;
    private final ViewRenderer csvViewRenderer;
    private final ViewRenderer xlsViewRenderer;
    private final UserRepository userRepository;

    @Transactional
    public ReportingResponse generateReport(String reportTypeAsString, ReportingRequest reportingRequest) {
        ReportType reportType = ReportType.from(reportTypeAsString);
        List<Report> reports = reportingRequest.getUserIds()
                .stream()
                .flatMap(userId -> userRepository.generateReport(userId, reportingRequest.getRange()))
                .collect(Collectors.toList());

        switch (reportType) {
            case CSV:
                return csvViewRenderer.writeToFile(reports, reportingRequest.getRange());
            case XLS:
                return xlsViewRenderer.writeToFile(reports, reportingRequest.getRange());
            default:
                throw new ReportingException(ERROR_CODE, "No suitable report type is provided");
        }
    }
}
