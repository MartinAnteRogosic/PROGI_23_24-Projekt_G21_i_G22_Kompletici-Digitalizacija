package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.Photo;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import hr.fer.progi.backend.repositroy.DocumentRepository;
import hr.fer.progi.backend.repositroy.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final PhotoRepository photoRepository;


    public List<DocumentEntity> getDocumentsByType(String documentType) {
        return documentRepository.findByDocumentType(documentType);
    }

    public DocumentEntity getDocumentById(String documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }

    @Override
    public ChangeCategoryDto getPhotoAndDocument(ChangeCategoryDto changeCategoryDto) {
        Photo photo = photoRepository.findPhotoByDocumentId(changeCategoryDto.getDocumentId())
                .orElseThrow(() ->
                        new PhotoNotFoundException("Could not find a photo related to this document")
                );

        Document document = documentRepository.findById(changeCategoryDto.getDocumentId())
                .orElseThrow(() ->
                        new DocumentNotFoundException("Document could not be found"));

        return ChangeCategoryDto.builder()
                .documentUrl(document.getUrl())
                .photoUrl(photo.getUrl())
                .build();
    }

    @Override
    public String changeCategory(ChangeCategoryDto changeCategoryDto) {
        Document document = documentRepository.findById(changeCategoryDto.getDocumentId())
                .orElseThrow(()-> new DocumentNotFoundException("Document could not be found"));

        document.setType(changeCategoryDto.getNewDocumentType());

        documentRepository.save(document);

        return "Successfully changed document type";
    }


}
