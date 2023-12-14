package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.ArchiveOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveOfferRepository extends JpaRepository<ArchiveOfferEntity, String> {
    // Additional custom queries can be added here if needed
}
