package hr.fer.progi.backend.service;

import hr.fer.progi.backend.dto.ChangePasswordRequestDto;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.StatisticDto;
import hr.fer.progi.backend.entity.EmployeeEntity;

import java.security.Principal;
import java.util.List;

public interface EmployeeService {

    void changePassword(ChangePasswordRequestDto requestDto, Principal connectedEmployee);


    EmployeeDto mapToDto(EmployeeEntity employeeEntity);
    EmployeeDto mapToDtoForGetAll(EmployeeEntity employeeEntity);
    EmployeeEntity mapToEntity(EmployeeDto employeeDto);

    List<EmployeeDto> getAllRevisers();
   long getTimeForEmployee(Long employeeId);

     StatisticDto getStatsForEmployee(Long employeeId);


    }
