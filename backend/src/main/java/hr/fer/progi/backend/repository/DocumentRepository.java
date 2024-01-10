package hr.fer.progi.backend.repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    List<DocumentEntity> findByType(DocumentType documentType);

    @Query("SELECT d FROM DocumentEntity d WHERE d.id = :userId")
    List<DocumentEntity> findAllById(Long userId);

    List<DocumentEntity> findByScanEmployeeId(Long employeeId);

    @Query("SELECT d FROM DocumentEntity d WHERE d.verified = true AND (d.toBeSigned = false OR d.toBeSigned IS NULL)")
    List<DocumentEntity> findAllVerifiedDocuments();



    @Transactional
    @Modifying
    @Query("UPDATE DocumentEntity d SET d.toBeSigned = true WHERE d.id = :documentId")
    void setDocumentTOBeSingedOnTrue(Long documentId);

    @Query("SELECT d FROM DocumentEntity d WHERE d.verified = true AND d.toBeSigned = true")
    List<DocumentEntity> findDocumentsToBeSigned();

    @Transactional
    @Modifying
    @Query("UPDATE DocumentEntity d SET d.signed = true, d.toBeSigned = false WHERE d.id = :documentId")
    void setDocumentSignOnTrue(Long documentId);

    @Transactional
    @Modifying
    @Query("UPDATE DocumentEntity d SET d.toBeSigned = false WHERE d.id = :documentId")
    void setDocumentTOBeSingedOnFalse(Long documentId);
}
