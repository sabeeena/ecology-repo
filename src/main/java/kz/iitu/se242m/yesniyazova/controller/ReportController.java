package kz.iitu.se242m.yesniyazova.controller;

import kz.iitu.se242m.yesniyazova.entity.Report;
import kz.iitu.se242m.yesniyazova.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.listAllReports();
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable Long id) {
        return reportService.getReportById(id).orElseThrow();
    }

    @PostMapping
    public Report create(@RequestBody Report report) {
        return reportService.saveReport(report);
    }

    @PutMapping("/{id}")
    public Report update(@PathVariable Long id, @RequestBody Report report) {
        return reportService.update(id, report);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reportService.delete(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Report> saveReport(@RequestBody Report report) {
        return ResponseEntity.ok(reportService.saveReport(report));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Report>> getReportsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.getReportsByUser(userId));
    }

    @PutMapping("/{reportId}/status")
    public ResponseEntity<Report> updateStatus(
            @PathVariable Long reportId,
            @RequestParam String newStatus
    ) {
        return ResponseEntity.ok(reportService.updateStatus(reportId, newStatus));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getReportsCount() {
        return ResponseEntity.ok(reportService.getReportsCount());
    }
}

