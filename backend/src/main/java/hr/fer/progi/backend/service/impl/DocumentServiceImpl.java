package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.entity.Document;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.Photo;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.PhotoRepository;
import hr.fer.progi.backend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final PhotoRepository photoRepository;


    @Override
    public List<Document> getDocumentsByType(DocumentType documentType) {
        return documentRepository.findByType(documentType);
    }

    @Override
    public Document getDocumentById(Long documentId) {
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
