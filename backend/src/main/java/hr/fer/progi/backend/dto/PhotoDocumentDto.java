package hr.fer.progi.backend.dto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.PhotoEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PhotoDocumentDto {
    private PhotoEntity photo;
    private DocumentEntity document;
}
