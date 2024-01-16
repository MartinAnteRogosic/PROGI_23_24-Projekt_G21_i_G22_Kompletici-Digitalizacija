package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.ChooseReviserDto;
import hr.fer.progi.backend.dto.DocumentDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.DocumentType;
import hr.fer.progi.backend.entity.EmployeeEntity;
import hr.fer.progi.backend.entity.PhotoEntity;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repository.DocumentRepository;
import hr.fer.progi.backend.repository.EmployeeRepository;
import hr.fer.progi.backend.repository.PhotoRepository;
import hr.fer.progi.backend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    public List<PhotoDocumentDto> getDocumentsByType(DocumentType documentType) {
        List<DocumentEntity> documents = documentRepository.findByType(documentType);
        return generatePhotoDocumentDtos(documents);

    }

    @Override
    public DocumentEntity getDocumentById(Long documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }


    @Override
    public String changeDocumentType(DocumentDto documentDto) {

        DocumentEntity documentEntity = documentRepository.findById(documentDto.getId())
                .orElseThrow(() -> new DocumentNotFoundException("Document could not be found"));

        documentEntity.setType(documentDto.getType());

        documentRepository.save(documentEntity);

        return String.format("Type of document %s has been changed to %s", documentEntity.getId(), documentEntity.getType());
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
    public List<PhotoDocumentDto> getRevisionDocuments(Principal connectedEmployee) {
        EmployeeEntity employeeEntity = (EmployeeEntity) ((UsernamePasswordAuthenticationToken) connectedEmployee).getPrincipal();
        List<DocumentEntity> documents = documentRepository.findByValidationEmployeeIdAndVerifiedIsFalse(employeeEntity.getId());

        return generatePhotoDocumentDtos(documents);
    }

    @Override
    public List<PhotoDocumentDto> getAllDocumentsForSigning() {
        List<DocumentEntity> documents = documentRepository.findByToBeSignedIsTrueAndVerifiedIsTrue();

        return generatePhotoDocumentDtos(documents);

    }

    @Override
    public List<PhotoDocumentDto> getDocumentHistory(Principal connectedEmployee) {

        EmployeeEntity employeeEntity = (EmployeeEntity) ((UsernamePasswordAuthenticationToken) connectedEmployee).getPrincipal();
        List<DocumentEntity> documents = documentRepository.findByScanEmployeeId(employeeEntity.getId());

        return generatePhotoDocumentDtos(documents);
    }

    @Override
    public String signDocument(DocumentDto documentDto) {

        DocumentEntity document = documentRepository.findById(documentDto.getId()).orElseThrow(() -> new DocumentNotFoundException("Document not found"));
        document.setSigned(documentDto.getSigned());
        document.setToBeSigned(false);

        documentRepository.save(document);

        return String.format("Document %s has been signed", document.getId());
    }

    @Override
    public List<PhotoDocumentDto> getAllDocuments() {
        List<DocumentEntity> documents = documentRepository.findAll();

        List<PhotoDocumentDto> photoDocumentDtos = generatePhotoDocumentDtos(documents).stream()
                .map(dto->{
                    DocumentEntity document = documentRepository.findById(dto.getDocumentId())
                            .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

                    EmployeeEntity employee = document.getScanEmployee();
                    EmployeeDto employeeDto = EmployeeDto.builder()
                            .id(employee.getId())
                            .firstName(employee.getFirstName())
                            .lastName(employee.getLastName())
                            .build();

                    dto.setEmployeeDto(employeeDto);
                    return dto;

                }).collect(Collectors.toList());

        return photoDocumentDtos;
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

        if (matcher.find()) {
            String documentTag = matcher.group();
            if (documentTag.startsWith("INT")) {
                return DocumentType.INTERNAL_DOCUMENT;
            } else if (documentTag.startsWith("P")) {
                return DocumentType.OFFER;
            } else if (documentTag.startsWith("R")) {
                return DocumentType.RECEIPT;
            }
        }

        return null;
    }

    @Override
    public List<PhotoDocumentDto> getAllUnconfirmedDocuments() {

        List<DocumentEntity> documents = documentRepository.findByCorrectIsNull();

        return generatePhotoDocumentDtos(documents);
    }


    public DocumentDto mapDocumentEntityToDto(DocumentEntity documentEntity) {
        return DocumentDto.builder()
                .id(documentEntity.getId())
                .url(documentEntity.getUrl())
                .build();
    }

    public List<PhotoDocumentDto> generatePhotoDocumentDtos(List<DocumentEntity> documents) {
        return documents.stream()
                .map(document -> {
                    PhotoEntity photo = document.getPhoto();

                    return PhotoDocumentDto.builder()
                            .documentId(document.getId())
                            .documentUrl(document.getUrl())
                            .documentName(document.getFileName())
                            .photoId(photo.getPhotoID())
                            .photoUrl(photo.getUrl())
                            .photoName(photo.getImageName())
                            .build();
                }).collect(Collectors.toList());
    }
}



