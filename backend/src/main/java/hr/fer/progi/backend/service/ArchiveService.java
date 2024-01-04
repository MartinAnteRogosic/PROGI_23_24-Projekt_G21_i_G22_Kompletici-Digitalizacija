package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.AllArchiveDocumentsDto;

public interface ArchiveService {
    void archiveDocument(Long documentID);

    AllArchiveDocumentsDto getAllArchivedDocuments();
}
