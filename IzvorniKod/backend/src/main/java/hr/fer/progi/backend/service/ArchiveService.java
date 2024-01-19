package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ArchiveDeleteDto;
import hr.fer.progi.backend.dto.PhotoDocumentDto;

import java.io.IOException;
import java.util.List;

public interface ArchiveService {
    String archiveDocument(Long documentID);

    List<PhotoDocumentDto> getAllArchivedDocuments();

    String deleteDocument(ArchiveDeleteDto archiveDeleteDto) throws IOException;
}
