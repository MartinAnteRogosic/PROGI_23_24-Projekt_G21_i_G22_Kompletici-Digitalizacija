package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.AllArchiveDocumentsDto;
import hr.fer.progi.backend.dto.ArchiveDeleteDto;
import hr.fer.progi.backend.entity.*;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.exception.EmployeeNotFoundException;
import hr.fer.progi.backend.repository.*;
import hr.fer.progi.backend.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArchiveServiceImpl implements ArchiveService {


    private final ArchiveReceiptRepository archiveReceiptRepository;
    private final ArchiveOfferRepository archiveOfferRepository;
    private final ArchiveInternalDocRepository archiveInternalDocRepository;
    private final DocumentRepository documentRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void archiveDocument(Long documentID) {
        // Retrieve the document from the DocumentEntity
        DocumentEntity documentEntity = documentRepository.findById(documentID)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + documentID));

        // Create ArchiveRecieptEntity and save
        ArchiveReceiptEntity archiveReciept = new ArchiveReceiptEntity();
        archiveReciept.setArcRecID(1L); // Set a generated ID
        archiveReciept.setClientName("ClientName"); // Set client name
        archiveReciept.setTotalPrice(100.0f); // Set total price
        archiveReciept.setDocument(documentEntity);
        archiveReciept.setDocumentType(DocumentType.RECEIPT);
        archiveReceiptRepository.save(archiveReciept);

        // Create ArchiveOfferEntity and save
        ArchiveOfferEntity archiveOffer = new ArchiveOfferEntity();
        archiveOffer.setArcOfferID(1L); // Set a generated ID
        archiveOffer.setTotalPrice(150.0f); // Set total price
        archiveOffer.setDocument(documentEntity); // Set the document
        archiveOffer.setDocumentType(DocumentType.OFFER);
        archiveOfferRepository.save(archiveOffer);

        // Create ArchiveInternalDocEntity and save
        ArchiveInternalDocEntity archiveInternalDoc = new ArchiveInternalDocEntity();
        archiveInternalDoc.setArchIntDocID(1L); // Set a generated ID
        archiveInternalDoc.setText("Internal Document Text"); // Set internal document text
        archiveInternalDoc.setDocument(documentEntity); // Set the document
        archiveInternalDoc.setDocumentType(DocumentType.INTERNAL_DOCUMENT);
        archiveInternalDocRepository.save(archiveInternalDoc);

        documentRepository.save(documentEntity);
    }

    @Override
    public AllArchiveDocumentsDto getAllArchivedDocuments() {
        List<ArchiveInternalDocEntity> archiveInternalDocEntities = archiveInternalDocRepository.findAll();
        List<ArchiveOfferEntity> archiveOfferEntities = archiveOfferRepository.findAll();
        List<ArchiveReceiptEntity> archiveReceiptEntities = archiveReceiptRepository.findAll();

        return AllArchiveDocumentsDto.builder()
                .archiveInternalDocEntities(archiveInternalDocEntities)
                .archiveOfferEntities(archiveOfferEntities)
                .archiveReceiptEntities(archiveReceiptEntities)
                .build();
    }

    @Override
    public String deleteDocument(ArchiveDeleteDto archiveDeleteDto) {
        EmployeeEntity director = employeeRepository.findByRole(Role.DIRECTOR).orElseThrow(
                ()-> new EmployeeNotFoundException("Director could not be found"));


        if(!passwordEncoder.matches(archiveDeleteDto.getDirectorPassword(), director.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }

        if(archiveDeleteDto.getDocumentType().equals(DocumentType.INTERNAL_DOCUMENT)){
            ArchiveInternalDocEntity archiveInternalDoc = archiveInternalDocRepository.findById(archiveDeleteDto.getDocumentId())
                    .orElseThrow(()->new DocumentNotFoundException("Internal document could not be found in the archive"));
            archiveInternalDocRepository.delete(archiveInternalDoc);
        }
        else if(archiveDeleteDto.getDocumentType().equals(DocumentType.OFFER)){
            ArchiveOfferEntity archiveOffer = archiveOfferRepository.findById(archiveDeleteDto.getDocumentId())
                    .orElseThrow(()->new DocumentNotFoundException("Offer document could not be found in the archive"));
            archiveOfferRepository.delete(archiveOffer);
        }
        else if(archiveDeleteDto.getDocumentType().equals(DocumentType.RECEIPT)){
            ArchiveReceiptEntity archiveReceipt = archiveReceiptRepository.findById(archiveDeleteDto.getDocumentId())
                    .orElseThrow(()->new DocumentNotFoundException("Receipt document could not be found in the archive"));
            archiveReceiptRepository.delete(archiveReceipt);
        }

        return "Document deleted successfully";
    }
}
