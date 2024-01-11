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
@Table(name = "ArchiveReciept")
public class ArchiveReceiptEntity {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arcRecID;

    private DocumentType documentType;

    @Column(name = "clientName")
    private String clientName;

    @Column(name = "totalPrice")
    private Float totalPrice;



    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DocumentEntity document;

}
