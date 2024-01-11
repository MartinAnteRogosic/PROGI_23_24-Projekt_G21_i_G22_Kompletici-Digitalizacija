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

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "totalPrice")
    private Float totalPrice;


    @OneToOne
    @JoinColumn(name = "doucment_id", referencedColumnName = "id")
    private DocumentEntity document;

}

