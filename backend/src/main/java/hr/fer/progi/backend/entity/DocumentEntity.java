package hr.fer.progi.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DocumentEntity")
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
    private Employee user;

}

