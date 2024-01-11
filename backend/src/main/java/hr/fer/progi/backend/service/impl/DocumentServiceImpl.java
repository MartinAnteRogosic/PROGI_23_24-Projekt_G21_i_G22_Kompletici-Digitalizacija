package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.PhotoEntity;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.PhotoRepository;
import hr.fer.progi.backend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final PhotoRepository photoRepository;


    @Override
    public List<DocumentEntity> getDocumentsByType(DocumentType documentType) {
        return documentRepository.findByType(documentType);

    }

    @Override
    public DocumentEntity getDocumentById(Long documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }

    @Override
    public ChangeCategoryDto getPhotoAndDocument(ChangeCategoryDto changeCategoryDto) {
        PhotoEntity photo = photoRepository.findPhotoByDocumentId(changeCategoryDto.getDocumentId())
                .orElseThrow(() ->
                        new PhotoNotFoundException("Could not find a photo related to this document")
                );

        DocumentEntity documentEntity = documentRepository.findById(changeCategoryDto.getDocumentId())
                .orElseThrow(() ->
                        new DocumentNotFoundException("Document could not be found"));

        return ChangeCategoryDto.builder()
                .documentUrl(documentEntity.getUrl())
                .photoUrl(photo.getUrl())
                .build();
    }

    @Override
    public String changeCategory(ChangeCategoryDto changeCategoryDto) {
        DocumentEntity documentEntity = documentRepository.findById(changeCategoryDto.getDocumentId())
                .orElseThrow(()-> new DocumentNotFoundException("Document could not be found"));

        documentEntity.setType(changeCategoryDto.getNewDocumentType());

        documentRepository.save(documentEntity);

        return "Successfully changed document type";
    }

   @Override
   public List<DocumentEntity> getAllDocumentsForUser(Long userId) {
       return documentRepository.findAllById(userId);
   }

    @Override
    public List<DocumentDto> getDocumentsByVerificationEmployeeId(Long employeeId) {
        List<DocumentEntity> documents = documentRepository.findByValidationEmployeeIdAndVerifiedIsFalse(employeeId);

        return documents.stream().map(
                this::mapDocumentEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PhotoEntity getPhotoById(Long photoId) {
        return photoRepository.findById(photoId).orElse(null);

    }

    @Override
    public List<DocumentEntity> getAllVerifedDocuments() {
        return documentRepository.findAllVerifiedDocuments();
    }

    @Override
   public void setDocumentToBeSinged(Long documentId) {
        DocumentEntity document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("document not found"));
            document.setToBeSigned(true);
            documentRepository.save(document);
    }

    @Override
    public List<DocumentEntity> getAllDocumentsForSign() {
        return documentRepository.findDocumentsToBeSigned();

    }

    @Override
    public void signDocument(Long documentId) {
        DocumentEntity document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("document not found"));
        document.setSigned(true);
        document.setToBeSigned(false);
        documentRepository.save(document);
    }

    @Override
    public void refuseSign(Long documentId) {
        DocumentEntity document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("document not found"));
        document.setToBeSigned(false);
        documentRepository.save(document);
    }

    @Override
    public List<DocumentEntity> getAllDocuments() {
        return documentRepository.findAll();
    }


    public DocumentDto mapDocumentEntityToDto(DocumentEntity documentEntity) {
        return DocumentDto.builder()
                .id(documentEntity.getId())
                .type(documentEntity.getType())
                .url(documentEntity.getUrl())
                .build();
    }
}



