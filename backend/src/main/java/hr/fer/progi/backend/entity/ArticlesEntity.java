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
    private ArchiveRecieptEntity archiveReciept;

    @ManyToOne
    @JoinColumn(name = "arcOfferID", referencedColumnName = "arcOfferID", insertable = false, updatable = false)
    private ArchiveOfferEntity archiveOffer;

    // Getters and setters

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getArcRecID() {
        return arcRecID;
    }

    public void setArcRecID(String arcRecID) {
        this.arcRecID = arcRecID;
    }

    public String getArcOfferID() {
        return arcOfferID;
    }

    public void setArcOfferID(String arcOfferID) {
        this.arcOfferID = arcOfferID;
    }

    public ArchiveRecieptEntity getArchiveReciept() {
        return archiveReciept;
    }

    public void setArchiveReciept(ArchiveRecieptEntity archiveReciept) {
        this.archiveReciept = archiveReciept;
    }

    public ArchiveOfferEntity getArchiveOffer() {
        return archiveOffer;
    }

    public void setArchiveOffer(ArchiveOfferEntity archiveOffer) {
        this.archiveOffer = archiveOffer;
    }
}
