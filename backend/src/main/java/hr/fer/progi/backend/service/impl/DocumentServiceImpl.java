package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.ChangeCategoryDto;
import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.PhotoEntity;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.exception.PhotoNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.repository.PhotoRepository;
import hr.fer.progi.backend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final PhotoRepository photoRepository;
    private final EmployeeRepository employeeRepository;


    @Override
    public List<DocumentDto> getDocumentsByType(DocumentType documentType) {
        List<DocumentEntity> documents = documentRepository.findByType(documentType);

        return documents.stream().map(
                this::mapDocumentEntityToDto)
                .collect(Collectors.toList());

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
    public List<DocumentEntity> getAllVerifiedDocuments() {
        return documentRepository.findAllVerifiedDocuments();
    }

    @Override
   public String setDocumentToBeSinged(DocumentDto documentDto) {
        DocumentEntity document = documentRepository.findById(documentDto.getId())
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

            document.setToBeSigned(documentDto.getToBeSigned());
            documentRepository.save(document);

            return String.format("Document %s has been sent to director for signing", document.getId());
    }

    @Override
    public List<DocumentDto> getAllDocumentsForSigning() {
        List<DocumentEntity> documents = documentRepository.findByToBeSignedIsTrueAndVerifiedIsTrue();

        return documents.stream().map(
                this::mapDocumentEntityToDto)
                .collect(Collectors.toList());

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

    @Override
    public void sendToReviser(ChooseReviserDto choosereviserdto) {
        DocumentEntity document = documentRepository.findById(choosereviserdto.getDocumentId())
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        EmployeeEntity reviser = employeeRepository.findById(choosereviserdto.getReviserId())
                .orElseThrow(() -> new EmployeeNotFoundException("Reviser not found"));

        document.setValidationEmployee(reviser);
        documentRepository.save(document);
    }

    @Override
    public String setCorrect(DocumentDto documentDto) {
        DocumentEntity document = documentRepository.findById(documentDto.getId())
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        document.setCorrect(documentDto.getCorrect());
        documentRepository.save(document);

        return String.format("Document %s is set to be %s", document.getId(), document.getCorrect() ? "correct" : "incorrect");
    }

    @Override
    public String setVerified(DocumentDto documentDto) {
        DocumentEntity document = documentRepository.findById(documentDto.getId())
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        document.setVerified(documentDto.getVerified());
        documentRepository.save(document);
        return String.format("Document %s has been verified", document.getId());
    }

    @Override
    public DocumentType categorizeDocument(String documentText) {

        String patternString = "P\\d{9}|R\\d{6}|INT\\d{4}";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(documentText);

        if(matcher.find()){
            String documentTag = matcher.group();
            if(documentTag.startsWith("INT")){
                return DocumentType.INTERNAL_DOCUMENT;
            } else if(documentTag.startsWith("P")){
                return DocumentType.OFFER;
            } else if(documentTag.startsWith("R")){
                return DocumentType.RECEIPT;
            }
        }

        return null;
    }


    public DocumentDto mapDocumentEntityToDto(DocumentEntity documentEntity) {
        return DocumentDto.builder()
                .id(documentEntity.getId())
                .url(documentEntity.getUrl())
                .build();
    }
}



