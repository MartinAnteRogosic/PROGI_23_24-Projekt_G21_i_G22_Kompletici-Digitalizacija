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
@Table(name = "ArchiveInternalDoc")
public class ArchiveInternalDocEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long archIntDocID;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String text;

    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private DocumentEntity document;


}
