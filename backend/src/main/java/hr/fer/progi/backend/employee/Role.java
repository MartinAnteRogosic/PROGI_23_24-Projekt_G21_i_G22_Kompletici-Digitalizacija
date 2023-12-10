package hr.fer.progi.backend.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hr.fer.progi.backend.employee.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    EMPLOYEE(Set.of(
            EMPLOYEE_READ,
            EMPLOYEE_CREATE,
            EMPLOYEE_UPDATE,
            EMPLOYEE_DELETE
    )),
    REVISER(Collections.emptySet()),
    ACCOUNTANT(Collections.emptySet()),
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
