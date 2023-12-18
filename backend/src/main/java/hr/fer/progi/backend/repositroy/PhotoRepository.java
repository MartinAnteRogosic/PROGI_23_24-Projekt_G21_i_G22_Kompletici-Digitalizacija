package hr.fer.progi.backend.repositroy;

import hr.fer.progi.backend.entity.Photo;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findPhotoByDocumentId(Long documentId);
}
