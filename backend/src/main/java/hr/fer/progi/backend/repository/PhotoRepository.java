package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

    Optional<PhotoEntity> findPhotoByDocumentId(Long documentId);
}
