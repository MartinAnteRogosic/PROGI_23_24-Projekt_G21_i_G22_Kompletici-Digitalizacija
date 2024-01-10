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
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentType type;

    private String url;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "scan_employee_id", nullable = true)
    private EmployeeEntity scanEmployee;

    private Boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validation_employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeEntity validationEmployee;

    private Boolean superVerified;

    private Boolean signed;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "photoID")
    private PhotoEntity photo;

    @OneToOne(mappedBy = "document")
    private ArchiveInternalDocEntity archiveInternalDocEntity;

    @OneToOne(mappedBy = "document")
    private ArchiveOfferEntity archiveOfferEntity;

    @OneToOne(mappedBy = "document")
    private ArchiveReceiptEntity archiveReceiptEntity;


}
