package kz.iitu.se242m.yesniyazova.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "report_parameters")
@Data
public class ReportParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "region", referencedColumnName = "id")
    private Region region;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}

