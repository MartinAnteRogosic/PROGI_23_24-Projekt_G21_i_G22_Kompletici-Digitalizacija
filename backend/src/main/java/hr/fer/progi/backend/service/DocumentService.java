package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.PhotoEntity;

import java.util.List;

public interface DocumentService {

    List<DocumentEntity> getDocumentsByType(DocumentType documentType);

    DocumentEntity getDocumentById(Long documentId);

    ChangeCategoryDto getPhotoAndDocument(ChangeCategoryDto changeCategoryDto);

    String changeCategory(ChangeCategoryDto changeCategoryDto);
    List<DocumentEntity> getAllDocumentsForUser(Long userId);
    PhotoEntity getPhotoById(Long photoId);

}
