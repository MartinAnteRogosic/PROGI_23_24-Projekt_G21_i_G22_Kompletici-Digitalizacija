package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.entity.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArchiveInternalDocDto {

    private Long archiveInternalDocId;
    private DocumentType documentType;
    private String documentUrl;
}
