package hr.fer.progi.backend.dto;
import hr.fer.progi.backend.entity.DocumentEntity;
import hr.fer.progi.backend.entity.PhotoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class PhotoDocument {
    private PhotoEntity photo;
    private DocumentEntity document;
}
