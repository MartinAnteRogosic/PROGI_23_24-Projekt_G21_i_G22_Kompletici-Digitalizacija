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
@Table(name = "ArchiveOffer")
public class ArchiveOfferEntity {

    @Id
    @Column(name = "arcOfferID", length = 10, nullable = true)
    private String arcOfferID;

    @Column(name = "totalPrice", nullable = true)
    private Float totalPrice;

    @Column(name = "documentID", length = 10, nullable = true)
    private String documentID;

    @ManyToOne
    @JoinColumn(name = "doucment_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Document document;

}

