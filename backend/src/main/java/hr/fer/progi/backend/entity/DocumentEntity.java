package hr.fer.progi.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
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

    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scan_employee_id")
    @JsonBackReference
    private EmployeeEntity scanEmployee;

    private Boolean correct;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verification_employee_id")
    @JsonBackReference
    private EmployeeEntity verificationEmployee;

    private Boolean verified;

    private Boolean signed;

    private Boolean toBeSigned;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "photoID")
    private PhotoEntity photo;

    @JsonIgnore
    @OneToOne(mappedBy = "document")
    private ArchiveEntity archive;




}
