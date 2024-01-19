package hr.fer.progi.backend.service.impl;

import hr.fer.progi.backend.dto.*;
import hr.fer.progi.backend.entity.*;
import hr.fer.progi.backend.exception.DocumentNotFoundException;
import hr.fer.progi.backend.repository.*;
import hr.fer.progi.backend.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArchiveServiceImpl implements ArchiveService {

    private final DocumentRepository documentRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudStorageServiceImpl cloudStorageService;
    private final PhotoRepository photoRepository;
    private final ArchiveRepository archiveRepository;

    @Override
    public String archiveDocument(Long documentID) {

        DocumentEntity documentEntity = documentRepository.findById(documentID)
                .orElseThrow(() -> new DocumentNotFoundException(String.format("Document with ID %d could not be found", documentID)));

        ArchiveEntity archiveEntity = ArchiveEntity.builder()
                .document(documentEntity)
                .build();

        archiveRepository.save(archiveEntity);

        return "Document successfully archived";
    }

    @Override
    public List<PhotoDocumentDto> getAllArchivedDocuments() {

        List<ArchiveEntity> archive = archiveRepository.findAll();

        return archive.stream()
                .map(entity -> {
                    DocumentEntity document = entity.getDocument();
                    PhotoEntity photo = document.getPhoto();

                    return PhotoDocumentDto.builder()
                            .documentId(document.getId())
                            .documentName(document.getFileName())
                            .documentUrl(document.getUrl())
                            .photoId(photo.getPhotoID())
                            .photoName(photo.getImageName())
                            .photoUrl(photo.getUrl())
                            .build();
                }).collect(Collectors.toList());

    }

    @Override
    public String deleteDocument(ArchiveDeleteDto archiveDeleteDto) throws IOException {
        EmployeeEntity director = employeeRepository.findByRole(Role.DIRECTOR).get(0);


        if(!passwordEncoder.matches(archiveDeleteDto.getDirectorPassword(), director.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }

        ArchiveEntity archiveEntity = archiveRepository.findByDocumentId(archiveDeleteDto.getDocumentId())
                .orElseThrow(()->new DocumentNotFoundException("Archive document could not be found"));

        deleteFromCloud(archiveEntity.getDocument().getId());
        archiveRepository.delete(archiveEntity);


        return "Document deleted successfully";
    }


    public void deleteFromCloud(Long documentId) throws IOException {
        DocumentEntity document = documentRepository.findById(documentId)
                .orElseThrow(()->new DocumentNotFoundException("Document could not be found"));

        PhotoEntity photo = photoRepository.findById(document.getPhoto().getPhotoID())
                .orElseThrow(()->new DocumentNotFoundException("Photo could not be found"));

        cloudStorageService.deleteFile(photo.getImageName());
        cloudStorageService.deleteFile(document.getFileName());
    }
}
