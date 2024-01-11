package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.entity.DocumentType;
import lombok.Data;

@Data
public class ArchiveDeleteDto {

    private String directorPassword;
    private Long archiveId;
    private DocumentType documentType;
}
