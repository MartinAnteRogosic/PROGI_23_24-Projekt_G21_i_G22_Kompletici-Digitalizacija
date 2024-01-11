package hr.fer.progi.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {


    DELETE_EMPLOYEE_ACCOUNT("delete-employee-account"),
    DELETE_ARCHIVE_DOCUMENT("delete-archive-document"),
    ALL_EMPLOYEE_STATISTICS("all-employee-statistics"),
    EMPLOYEE_STATISTICS("employee-statistics"),
    CHANGE_DOCUMENT_CATEGORY("change-document-category")

    ;


    private final String permission;
}
