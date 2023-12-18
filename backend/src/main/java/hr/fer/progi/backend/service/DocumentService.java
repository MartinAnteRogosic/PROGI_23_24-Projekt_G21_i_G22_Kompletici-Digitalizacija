package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangeCategoryDto;

public interface DocumentService {

    ChangeCategoryDto getPhotoAndDocument(ChangeCategoryDto changeCategoryDto);

    String  changeCategory(ChangeCategoryDto changeCategoryDto);
}
