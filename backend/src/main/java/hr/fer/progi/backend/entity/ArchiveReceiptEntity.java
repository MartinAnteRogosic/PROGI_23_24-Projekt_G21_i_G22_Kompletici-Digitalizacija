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
    @Column(name = "arcRecID", length = 10, nullable = true)
    private String arcRecID;

    @Column(name = "clientName", length = 50, nullable = true)
    private String clientName;

    @Column(name = "totalPrice", nullable = true)
    private Float totalPrice;

    @Column(name = "documentID", length = 10, nullable = true)
    private String documentID;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Document document;

}
