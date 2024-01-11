package hr.fer.progi.backend.dto;


import hr.fer.progi.backend.entity.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArchiveReceiptDto {

    private Long archiveReceiptId;
    private DocumentType documentType;
    private String documentUrl;
}
