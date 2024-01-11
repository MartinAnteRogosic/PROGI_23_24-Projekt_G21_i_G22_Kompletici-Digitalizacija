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
    public String archiveDocument(Long documentID) {
        // Retrieve the document from the DocumentEntity
        DocumentEntity documentEntity = documentRepository.findById(documentID)
                .orElseThrow(() -> new DocumentNotFoundException(String.format("Document with ID %d could not be found", documentID)));

        if(documentEntity.getType().equals(DocumentType.RECEIPT)){
            ArchiveReceiptEntity archiveReceipt = ArchiveReceiptEntity.builder()
                    .documentType(documentEntity.getType())
                    .document(documentEntity)
                    .build();
            archiveReceiptRepository.save(archiveReceipt);

            System.out.println(archiveReceipt);


        }
        else if(documentEntity.getType().equals(DocumentType.OFFER)){
            ArchiveOfferEntity archiveOffer = ArchiveOfferEntity.builder()
                    .documentType(documentEntity.getType())
                    .document(documentEntity)
                    .build();

            archiveOfferRepository.save(archiveOffer);
            System.out.println(archiveOffer);
        }
        else if(documentEntity.getType().equals(DocumentType.INTERNAL_DOCUMENT)){
            ArchiveInternalDocEntity archiveInternalDoc = ArchiveInternalDocEntity.builder()
                    .documentType(documentEntity.getType())
                    .document(documentEntity)
                    .build();

            archiveInternalDocRepository.save(archiveInternalDoc);
            System.out.println(archiveInternalDoc);
        }

        return "Document successfully archived";
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
            ArchiveInternalDocEntity archiveInternalDoc = archiveInternalDocRepository.findById(archiveDeleteDto.getArchiveId())
                    .orElseThrow(()->new DocumentNotFoundException("Internal document could not be found in the archive"));
            archiveInternalDocRepository.delete(archiveInternalDoc);
        }
        else if(archiveDeleteDto.getDocumentType().equals(DocumentType.OFFER)){
            ArchiveOfferEntity archiveOffer = archiveOfferRepository.findById(archiveDeleteDto.getArchiveId())
                    .orElseThrow(()->new DocumentNotFoundException("Offer document could not be found in the archive"));
            archiveOfferRepository.delete(archiveOffer);
        }
        else if(archiveDeleteDto.getDocumentType().equals(DocumentType.RECEIPT)){
            ArchiveReceiptEntity archiveReceipt = archiveReceiptRepository.findById(archiveDeleteDto.getArchiveId())
                    .orElseThrow(()->new DocumentNotFoundException("Receipt document could not be found in the archive"));
            archiveReceiptRepository.delete(archiveReceipt);
        }

        return "Document deleted successfully";
    }
}
