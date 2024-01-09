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
@Table(name = "Articles")
public class ArticlesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(name = "articleName", length = 50, nullable = false)
    private String articleName;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "arcRecID", length = 10, nullable = false)
    private String arcRecID;

    @Column(name = "arcOfferID", length = 10, nullable = false)
    private String arcOfferID;

    @ManyToOne
    @JoinColumn(name = "arcRecID", referencedColumnName = "arcRecID", insertable = false, updatable = false)
    private ArchiveReceiptEntity archiveReciept;

    @ManyToOne
    @JoinColumn(name = "arcOfferID", referencedColumnName = "arcOfferID", insertable = false, updatable = false)
    private ArchiveOfferEntity archiveOffer;

}
