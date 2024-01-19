package hr.fer.progi.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {

    EMPLOYEE(Collections.emptySet()),

    REVISER(Collections.emptySet()),

    ACCOUNTANT_RECEIPT(Collections.emptySet()),
    ACCOUNTANT_OFFER(Collections.emptySet()),
    ACCOUNTANT_INT_DOC(Collections.emptySet()),

    DIRECTOR(Collections.emptySet());

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
