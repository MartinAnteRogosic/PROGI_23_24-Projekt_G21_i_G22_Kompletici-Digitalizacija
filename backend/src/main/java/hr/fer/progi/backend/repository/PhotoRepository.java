package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

}
