package hr.fer.progi.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ArchiveReciept")
public class ArchiveRecieptEntity {

    @Id
    @Column(name = "arcRecID", length = 10, nullable = false)
    private String arcRecID;

    @Column(name = "clientName", length = 50, nullable = false)
    private String clientName;

    @Column(name = "totalPrice", nullable = false)
    private Float totalPrice;

    @Column(name = "documentID", length = 10, nullable = false)
    private String documentID;

    @ManyToOne
    @JoinColumn(name = "documentID", referencedColumnName = "documentID", insertable = false, updatable = false)
    private DocumentEntity document;

    // Getters and setters

    public String getArcRecID() {
        return arcRecID;
    }

    public void setArcRecID(String arcRecID) {
        this.arcRecID = arcRecID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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
