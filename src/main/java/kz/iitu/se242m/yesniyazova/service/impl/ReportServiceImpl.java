package kz.iitu.se242m.yesniyazova.service.impl;

import kz.iitu.se242m.yesniyazova.entity.Report;
import kz.iitu.se242m.yesniyazova.repository.ReportRepository;
import kz.iitu.se242m.yesniyazova.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    // Constructor Injection
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report saveReport(Report report) {
        report.setCreateDate(java.time.LocalDateTime.now());
        report.setStatus("PENDING");
        return reportRepository.save(report);
    }

    @Override
    public List<Report> getReportsByUser(Long userId) {
        return reportRepository.findAll().stream()
                .filter(r -> r.getAuthor().getId().equals(userId))
                .toList();
    }

    @Override
    public Report updateStatus(Long reportId, String newStatus) {
        Report rep = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        rep.setStatus(newStatus);
        return reportRepository.save(rep);
    }

    @Override
    public long getReportsCount() {
        return reportRepository.count();
    }

    public List<Report> listAllReports() {
       return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report update(Long id, Report report) {
        report.setId(id);
        return reportRepository.save(report);
    }

    public void delete(Long id) {
        reportRepository.deleteById(id);
    }
}
