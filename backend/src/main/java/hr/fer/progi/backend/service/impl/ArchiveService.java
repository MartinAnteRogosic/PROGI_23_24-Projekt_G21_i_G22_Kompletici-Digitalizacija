package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.entity.*;
import hr.fer.progi.backend.repository.ArchiveInternalDocRepository;
import hr.fer.progi.backend.repository.ArchiveOfferRepository;
import hr.fer.progi.backend.repository.ArchiveRecieptRepository;
import hr.fer.progi.backend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {

    @Autowired
    private ArchiveRecieptRepository archiveRecieptRepository;

    @Autowired
    private ArchiveOfferRepository archiveOfferRepository;

    @Autowired
    private ArchiveInternalDocRepository archiveInternalDocRepository;

    @Autowired
    private DocumentRepository documentRepository;

    public void archiveDocument(String documentID) {
        // Retrieve the document from the DocumentEntity
        Document document = documentRepository.findById(documentID)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + documentID));

        // Create ArchiveRecieptEntity and save
        ArchiveRecieptEntity archiveReciept = new ArchiveRecieptEntity();
        archiveReciept.setArcRecID("generatedArcRecID"); // Set a generated ID
        archiveReciept.setClientName("ClientName"); // Set client name
        archiveReciept.setTotalPrice(100.0f); // Set total price
        archiveReciept.setDocument(document); // Set the document
        archiveRecieptRepository.save(archiveReciept);

        // Create ArchiveOfferEntity and save
        ArchiveOfferEntity archiveOffer = new ArchiveOfferEntity();
        archiveOffer.setArcOfferID("generatedArcOfferID"); // Set a generated ID
        archiveOffer.setTotalPrice(150.0f); // Set total price
        archiveOffer.setDocument(document); // Set the document
        archiveOfferRepository.save(archiveOffer);

        // Create ArchiveInternalDocEntity and save
        ArchiveInternalDocEntity archiveInternalDoc = new ArchiveInternalDocEntity();
        archiveInternalDoc.setArchIntDocID("generatedArchIntDocID"); // Set a generated ID
        archiveInternalDoc.setText("Internal Document Text"); // Set internal document text
        archiveInternalDoc.setDocument(document); // Set the document
        archiveInternalDocRepository.save(archiveInternalDoc);

        documentRepository.save(document);
    }
}
