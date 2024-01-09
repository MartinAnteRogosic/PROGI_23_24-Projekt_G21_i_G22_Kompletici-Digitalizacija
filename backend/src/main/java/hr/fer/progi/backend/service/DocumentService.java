package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.Photo;

import java.util.List;

public interface DocumentService {

    List<Document> getDocumentsByType(DocumentType documentType);

    Document getDocumentById(Long documentId);

    ChangeCategoryDto getPhotoAndDocument(ChangeCategoryDto changeCategoryDto);

    String changeCategory(ChangeCategoryDto changeCategoryDto);
List<Document> getAllDocumentsForUser(Long userId);
Photo getPhotoById(Long photoId);

}
