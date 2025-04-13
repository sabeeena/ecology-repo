package kz.iitu.se242m.yesniyazova.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    private String status;
    private String format;
    private String path;

    @OneToOne
    @JoinColumn(name = "parameters", referencedColumnName = "id")
    private ReportParameters parameters;
}
