package hr.fer.progi.backend.dto;


import hr.fer.progi.backend.entity.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCategoryDto {

    private Long documentId;
    private String documentUrl;
    private String photoUrl;
    private DocumentType newDocumentType;


}
