package hr.fer.progi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UploadResponseDto {

    private Long photoId;
    private String photoUrl;
    private Long documentId;
    private String documentUrl;
}
