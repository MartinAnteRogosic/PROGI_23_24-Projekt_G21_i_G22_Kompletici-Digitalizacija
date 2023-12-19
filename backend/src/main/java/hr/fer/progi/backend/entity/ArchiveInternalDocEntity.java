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
    private Document document;


}
