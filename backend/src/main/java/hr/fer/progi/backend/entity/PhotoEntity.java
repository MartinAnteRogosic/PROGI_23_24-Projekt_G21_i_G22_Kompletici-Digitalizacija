package hr.fer.progi.backend.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

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
    private EmployeeEntity user;

    // Getters and setters

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
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
