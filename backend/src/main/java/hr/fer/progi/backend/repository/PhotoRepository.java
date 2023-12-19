package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findPhotoByDocumentId(Long documentId);
}
