package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.entity.ArchiveInternalDocEntity;
import hr.fer.progi.backend.entity.ArchiveOfferEntity;
import hr.fer.progi.backend.entity.ArchiveReceiptEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllArchiveDocumentsDto {

    List<ArchiveInternalDocDto> archiveInternalDocs;
    List<ArchiveOfferDto> archiveOffers;
    List<ArchiveReceiptDto> archiveReceipts;
}
