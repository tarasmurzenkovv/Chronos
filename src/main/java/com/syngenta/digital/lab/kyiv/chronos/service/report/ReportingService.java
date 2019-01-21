package com.syngenta.digital.lab.kyiv.chronos.service.report;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportType;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Report;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingResponse;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ReportingException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import com.syngenta.digital.lab.kyiv.chronos.service.report.view.ViewRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingService {
    private static final int ERROR_CODE = 17;
    private final ViewRenderer csvViewRenderer;
    private final ViewRenderer xlsViewRenderer;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ReportParameterValidationService reportParameterValidationService;

    @Transactional
    public ReportingResponse generateReport(String reportTypeAsString, ReportingRequest reportingRequest) {
        reportParameterValidationService.validate(reportingRequest);
        ReportType reportType = ReportType.from(reportTypeAsString);
        List<Report> reports = userRepository.generateReport(reportingRequest);
        freezeTasks(reports);
        switch (reportType) {
            case CSV:
                return csvViewRenderer.writeToFile(reports, reportingRequest.getRange());
            case XLS:
                return xlsViewRenderer.writeToFile(reports, reportingRequest.getRange());
            default:
                throw new ReportingException(ERROR_CODE, "No suitable report type is provided");
        }
    }

    private void freezeTasks(List<Report> reports) {
        if (CollectionUtils.isEmpty(reports)) {
            throw new ReportingException(ERROR_CODE, "No tasks have been found for the report generations.");
        }
        List<Long> taskIds = reports.stream().map(Report::getTaskId).collect(Collectors.toList());
        taskRepository.freezeTasks(taskIds);
    }
}
