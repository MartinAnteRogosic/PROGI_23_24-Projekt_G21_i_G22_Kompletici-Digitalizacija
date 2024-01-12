package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.AllArchiveDocumentsDto;
import hr.fer.progi.backend.dto.ArchiveDeleteDto;

import java.io.IOException;

public interface ArchiveService {
    String archiveDocument(Long documentID);

    AllArchiveDocumentsDto getAllArchivedDocuments();

    String deleteDocument(ArchiveDeleteDto archiveDeleteDto) throws IOException;
}
