package hr.fer.progi.backend.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_UPDATE("employee:update"),
    EMPLOYEE_CREATE("employee:create"),
    EMPLOYEE_DELETE("employee:delete")
    ;


    private final String permission;
}
