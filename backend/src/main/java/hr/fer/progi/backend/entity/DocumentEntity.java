package hr.fer.progi.backend.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Document")
public class DocumentEntity {

    @Id
    @Column(name = "documentID", length = 10, nullable = false)
    private String documentID;

    @Column(name = "verifierID", length = 10, nullable = false, unique = true)
    private String verifierID;

    @Column(name = "correct", nullable = false)
    private boolean correct;

    @Column(name = "documentType", length = 20, nullable = false)
    private String documentType;

    @Column(name = "signed", nullable = false)
    private boolean signed;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "superVerified", nullable = false)
    private boolean superVerified;

    @Column(name = "id", length = 10, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeEntity user;

    // Getters and setters

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getVerifierID() {
        return verifierID;
    }

    public void setVerifierID(String verifierID) {
        this.verifierID = verifierID;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isSuperVerified() {
        return superVerified;
    }

    public void setSuperVerified(boolean superVerified) {
        this.superVerified = superVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeEntity getUser() {
        return user;
    }

    public void setUser(EmployeeEntity user) {
        this.user = user;
    }
}

