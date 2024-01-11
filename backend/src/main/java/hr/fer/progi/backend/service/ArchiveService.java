package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.AllArchiveDocumentsDto;
import hr.fer.progi.backend.dto.ArchiveDeleteDto;

public interface ArchiveService {
    void archiveDocument(Long documentID);

    AllArchiveDocumentsDto getAllArchivedDocuments();

    String deleteDocument(ArchiveDeleteDto archiveDeleteDto);
}
