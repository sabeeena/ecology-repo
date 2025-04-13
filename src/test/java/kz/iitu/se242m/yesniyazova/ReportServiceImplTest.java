package kz.iitu.se242m.yesniyazova;

import kz.iitu.se242m.yesniyazova.entity.Report;
import kz.iitu.se242m.yesniyazova.entity.User;
import kz.iitu.se242m.yesniyazova.repository.ReportRepository;
import kz.iitu.se242m.yesniyazova.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    private User testUser;

    public ReportServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCountReports() {
        when(reportRepository.count()).thenReturn(5L);
        assertEquals(5L, reportService.getReportsCount());
    }

    @Test
    void saveReport_setsDefaultsAndPersists() {
        Report draft = new Report();
        draft.setAuthor(testUser);

        when(reportRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Report saved = reportService.saveReport(draft);

        assertThat(saved.getStatus()).isEqualTo("PENDING");
        assertThat(saved.getCreateDate()).isNotNull();
        verify(reportRepository).save(draft);
    }

//    @Test
//    void getReportsByUser_filtersByAuthorId() {
//        Report r1 = new Report(); r1.setAuthor(testUser);
//        Report r2 = new Report(); r2.setAuthor(new User());
//        when(reportRepository.findAll()).thenReturn(List.of(r1, r2));
//
//        List<Report> list = reportService.getReportsByUser(1L);
//
//        assertThat(list).containsExactly(r1);
//    }

    @Test
    void updateStatus_changesStatusAndSaves() {
        Report rep = new Report(); rep.setId(5L); rep.setStatus("OLD");
        when(reportRepository.findById(5L)).thenReturn(Optional.of(rep));
        when(reportRepository.save(rep)).thenReturn(rep);

        Report updated = reportService.updateStatus(5L, "DONE");

        assertThat(updated.getStatus()).isEqualTo("DONE");
        verify(reportRepository).save(rep);
    }

    @Test
    void listAllReports_returnsAll() {
        when(reportRepository.findAll()).thenReturn(List.of(new Report()));
        assertThat(reportService.listAllReports()).hasSize(1);
    }

    @Test
    void getReportById_found() {
        Report r = new Report(); r.setId(7L);
        when(reportRepository.findById(7L)).thenReturn(Optional.of(r));
        assertThat(reportService.getReportById(7L)).contains(r);
    }

    @Test
    void update_overwritesIdAndSaves() {
        Report incoming = new Report(); incoming.setTitle("x");
        Report saved    = new Report(); saved.setId(9L); saved.setTitle("x");
        when(reportRepository.save(incoming)).thenReturn(saved);

        Report result = reportService.update(9L, incoming);

        assertThat(result.getId()).isEqualTo(9L);
        verify(reportRepository).save(incoming);
    }

    @Test
    void delete_removesById() {
        reportService.delete(11L);
        verify(reportRepository).deleteById(11L);
    }
}

