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
    @Column(name = "arcOfferID", length = 10, nullable = false)
    private String arcOfferID;

    @Column(name = "totalPrice", nullable = false)
    private Float totalPrice;

    @Column(name = "documentID", length = 10, nullable = false)
    private String documentID;

    @ManyToOne
    @JoinColumn(name = "documentID", referencedColumnName = "documentID", insertable = false, updatable = false)
    private Document document;

}

