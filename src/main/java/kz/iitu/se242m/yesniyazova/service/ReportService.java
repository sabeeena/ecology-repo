package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report saveReport(Report report);
    List<Report> getReportsByUser(Long userId);
    Report updateStatus(Long reportId, String newStatus);
    long getReportsCount();
    List<Report> listAllReports();
    Optional<Report> getReportById(Long id);
    Report update(Long id, Report report);
    void delete(Long id);
}
