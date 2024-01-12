package hr.fer.progi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DocumentDto {

    private Long id;
    private String url;
    private Boolean correct;
    private Boolean verified;
    private Boolean signed;
    private Boolean toBeSigned;

}
