package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static hr.fer.progi.backend.entity.Role.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void EmployeeRepository_SaveAll_ReturnSavedEmployee(){

        //Arrange
        Employee employee = Employee.builder()
                .firstName("Ivan")
                .lastName("Horvat")
                .email("ivan.horvat@gmail.com")
                .password("1234")
                .role(EMPLOYEE)
                .build();

        //Act
        Employee savedEmployee = employeeRepository.save(employee);

        //Assert
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void EmployeeRepository_GetAll_ReturnsMoreThanOne(){
        Employee employee1 = Employee.builder()
                .firstName("Ivan")
                .lastName("Horvat")
                .email("ivan.horvat@gmail.com")
                .password("1234")
                .role(DIRECTOR)
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Pero")
                .lastName("Peric")
                .email("pero.peric@gmail.com")
                .password("1234")
                .role(EMPLOYEE)
                .build();

        Employee employee3 = Employee.builder()
                .firstName("Ivo")
                .lastName("Ivic")
                .email("ivo.ivic@gmail.com")
                .password("1234")
                .role(REVISER)
                .build();

        Employee savedEmployee1 = employeeRepository.save(employee1);
        Employee savedEmployee2 = employeeRepository.save(employee2);
        Employee savedEmployee3 = employeeRepository.save(employee3);

        List<Employee> employeeList = employeeRepository.findAll();

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(3);
    }

    @Test
    public void EmployeeRepository_FindById_ReturnsEmployee(){
        Employee employee = Employee.builder()
                .firstName("Ivan")
                .lastName("Horvat")
                .email("ivan.horvat@gmail.com")
                .password("1234")
                .role(DIRECTOR)
                .build();

        employeeRepository.save(employee);

        Employee employeeList = employeeRepository.findById(employee.getId()).get();

        Assertions.assertThat(employeeList).isNotNull();
    }
}
