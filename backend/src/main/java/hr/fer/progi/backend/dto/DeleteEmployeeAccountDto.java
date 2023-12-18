package hr.fer.progi.backend.dto;

import lombok.Data;

@Data
public class DeleteEmployeeAccountDto {

    private String directorPassword;
    private Long employeeId;
}
