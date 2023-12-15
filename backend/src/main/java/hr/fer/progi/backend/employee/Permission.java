package hr.fer.progi.backend.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.internal.DefaultLoadEventListener;

@Getter
@RequiredArgsConstructor
public enum Permission {


    DELETE_EMPLOYEE_ACCOUNT("delete-employee-account"),
    ALL_EMPLOYEE_STATISTICS("all-employee-statistics"),
    EMPLOYEE_STATISTICS("employee-statistics")

    ;


    private final String permission;
}
