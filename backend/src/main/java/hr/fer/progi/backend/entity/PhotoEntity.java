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
@Table(name = "Photos")
public class PhotoEntity {

    @Id
    @Column(name = "photoID", length = 10, nullable = false)
    private String photoID;

    @Column(name = "url", length = 255, nullable = false, unique = true)
    private String url;

    @Column(name = "imageName", length = 100, nullable = false)
    private String imageName;

    @Column(name = "uploadTime", nullable = false)
    private Timestamp uploadTime;

    @Column(name = "id", length = 10, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee user;

}
