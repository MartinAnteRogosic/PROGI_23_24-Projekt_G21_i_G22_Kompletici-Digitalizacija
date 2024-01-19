package hr.fer.progi.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hr.fer.progi.backend.entity.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    EMPLOYEE(Collections.emptySet()),

    REVISER(Set.of(
            CHANGE_DOCUMENT_CATEGORY
    )),

    ACCOUNTANT_RECEIPT(Set.of(
            ARCHIVE_DOCUMENT,
            ARCHIVE_ALL_DOCUMENTS,
            ARCHIVE_DELETE_DOCUMENT,
            DOCUMENT_GET_BY_TYPE,
            DOCUMENT_GET_BY_ID,
            DOCUMENT_SEND_TO_SIGN,
            DOCUMENT_CHANGE_CATEGORY
    )),
    ACCOUNTANT_OFFER(Set.of(
            ARCHIVE_DOCUMENT,
            ARCHIVE_ALL_DOCUMENTS,
            ARCHIVE_DELETE_DOCUMENT,
            DOCUMENT_GET_BY_TYPE,
            DOCUMENT_GET_BY_ID,
            DOCUMENT_SEND_TO_SIGN,
            DOCUMENT_CHANGE_CATEGORY
    )),
    ACCOUNTANT_INT_DOC(Set.of(
            ARCHIVE_DOCUMENT,
            ARCHIVE_ALL_DOCUMENTS,
            ARCHIVE_DELETE_DOCUMENT,
            DOCUMENT_GET_BY_TYPE,
            DOCUMENT_GET_BY_ID,
            DOCUMENT_SEND_TO_SIGN,
            DOCUMENT_CHANGE_CATEGORY
    )),

    DIRECTOR(Set.of(
            ALL_EMPLOYEE_STATISTICS,
            EMPLOYEE_STATISTICS,
            DELETE_EMPLOYEE_ACCOUNT,
            CHANGE_DOCUMENT_CATEGORY,
            DELETE_ARCHIVE_DOCUMENT
    ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}