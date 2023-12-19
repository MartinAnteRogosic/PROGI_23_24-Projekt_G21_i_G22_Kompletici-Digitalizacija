package hr.fer.progi.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentType type;

    private String url;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scan_employee_id")
    private Employee scanEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validation_employee_id")
    private Employee validationEmployee;

    private Boolean superVerified;

    private Boolean signed;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
