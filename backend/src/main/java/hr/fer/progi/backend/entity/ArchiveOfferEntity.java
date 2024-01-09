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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arcOfferID;

    @Column(name = "totalPrice", nullable = true)
    private Float totalPrice;

    @OneToOne
    @JoinColumn(name = "doucment_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DocumentEntity document;

}

