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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoID;

    private String url;

    private String imageName;

    private Timestamp uploadTime;

    @OneToOne(mappedBy = "photo")
    private DocumentEntity document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeEntity uploadEmployee;

}
