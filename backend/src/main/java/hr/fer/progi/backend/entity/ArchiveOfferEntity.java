package hr.fer.progi.backend.entity;

import jakarta.persistence.*;

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
    private DocumentEntity document;

    // Getters and setters

    public String getArcOfferID() {
        return arcOfferID;
    }

    public void setArcOfferID(String arcOfferID) {
        this.arcOfferID = arcOfferID;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public DocumentEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentEntity document) {
        this.document = document;
    }
}

