package kz.iitu.se242m.yesniyazova;

import kz.iitu.se242m.yesniyazova.entity.Region;
import kz.iitu.se242m.yesniyazova.repository.RegionRepository;
import kz.iitu.se242m.yesniyazova.service.impl.RegionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RegionServiceImplTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionServiceImpl regionService;

    public RegionServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRegions_returnsList() {
        when(regionRepository.findAll()).thenReturn(
                List.of(new Region(1L, "KZ", "Kazakhstan", null, null),
                        new Region(2L, "EU", "European Union", null, null))
        );

        List<Region> result = regionService.getAllRegions();

        assertThat(result).hasSize(2);
        verify(regionRepository).findAll();
    }

    @Test
    void saveRegion_persistsEntity() {
        Region toSave = new Region(null, "US", "United States", null, null);
        Region saved  = new Region(3L, "US", "United States", null, null);
        when(regionRepository.save(toSave)).thenReturn(saved);

        Region result = regionService.saveRegion(toSave);

        assertThat(result.getId()).isEqualTo(3L);
        verify(regionRepository).save(toSave);
    }
}
