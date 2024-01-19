package hr.fer.progi.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {


    //ARCHIVE
    ARCHIVE_DOCUMENT("archive-document"),
    ARCHIVE_ALL_DOCUMENTS("archive-all-documents"),
    ARCHIVE_DELETE_DOCUMENT("archive-delete-document"),

    //DOCUMENT
    DOCUMENT_GET_BY_TYPE("document-get-by-type"),
    DOCUMENT_GET_BY_ID("document-get-by-id"),
    DOCUMENT_SEND_TO_REVISER("send-to-reviser"),
    DOCUMENT_CORRECT("set-correct"),
    DOCUMENT_VERIFY("verify"),
    DOCUMENT_SEND_TO_SIGN("send-to-sign"),
    DOCUMENT_SIGN("sign"),
    DOCUMENT_CHANGE_CATEGORY("change-category"),


    //EMPLOYEE




    DELETE_EMPLOYEE_ACCOUNT("delete-employee-account"),
    DELETE_ARCHIVE_DOCUMENT("delete-archive-document"),
    ALL_EMPLOYEE_STATISTICS("all-employee-statistics"),
    EMPLOYEE_STATISTICS("employee-statistics"),
    CHANGE_DOCUMENT_CATEGORY("change-document-category")

    ;


    private final String permission;
}
