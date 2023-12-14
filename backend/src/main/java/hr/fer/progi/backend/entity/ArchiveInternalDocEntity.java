package hr.fer.progi.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ArchiveInternalDoc")
public class ArchiveInternalDocEntity {

    @Id
    @Column(name = "archIntDocID", length = 10, nullable = false)
    private String archIntDocID;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "documentID", length = 10, nullable = false)
    private String documentID;

    @ManyToOne
    @JoinColumn(name = "documentID", referencedColumnName = "documentID", insertable = false, updatable = false)
    private DocumentEntity document;

    // Getters and setters

    public String getArchIntDocID() {
        return archIntDocID;
    }

    public void setArchIntDocID(String archIntDocID) {
        this.archIntDocID = archIntDocID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
