package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.Range;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportType;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingResponse;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ApplicationBaseException;
import com.syngenta.digital.lab.kyiv.chronos.service.report.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class ReportingController {
    private final ReportingService reportingService;

    @SneakyThrows
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/reporting/{reportType}")
    public ResponseEntity<Resource> generateCsvReport(@PathVariable("reportType") ReportType reportType,
                                                      @RequestParam("id") List<Long> userIds,
                                                      @RequestParam("start")
                                                      @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate start,
                                                      @RequestParam("end")
                                                      @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate end) {
        ReportingRequest reportingRequest = new ReportingRequest(new Range(start, end), userIds);
        ReportingResponse reportingResponse = reportingService.generateReport(reportType, reportingRequest);
        HttpHeaders responseHeaders = buildHeaders(reportType);
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", reportingResponse.getFileName()));
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(new ByteArrayResource(reportingResponse.getContent()));
    }

    private HttpHeaders buildHeaders(ReportType reportType) {
        HttpHeaders responseHeaders = new HttpHeaders();
        switch (reportType) {
            case XLS:
                responseHeaders.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                break;
            case CSV:
                responseHeaders.setContentType(MediaType.parseMediaType("application/csv"));
                break;
            default:
                throw new ApplicationBaseException(10, "No suitable reporting type is provided");
        }
        return responseHeaders;
    }
}
