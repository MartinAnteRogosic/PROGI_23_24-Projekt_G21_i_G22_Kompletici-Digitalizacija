package hr.fer.progi.backend.repository;

import hr.fer.progi.backend.entity.EmployeeEntity;
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
public class EmployeeEntityRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void EmployeeRepository_SaveAll_ReturnSavedEmployee(){

        //Arrange
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName("Ivan")
                .lastName("Horvat")
                .email("ivan.horvat@gmail.com")
                .password("1234")
                .role(EMPLOYEE)
                .build();

        //Act
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);

        //Assert
        Assertions.assertThat(savedEmployeeEntity).isNotNull();
        Assertions.assertThat(savedEmployeeEntity.getId()).isGreaterThan(0);
    }

    @Test
    public void EmployeeRepository_GetAll_ReturnsMoreThanOne(){
        EmployeeEntity employeeEntity1 = EmployeeEntity.builder()
                .firstName("Ivan")
                .lastName("Horvat")
                .email("ivan.horvat@gmail.com")
                .password("1234")
                .role(DIRECTOR)
                .build();

        EmployeeEntity employeeEntity2 = EmployeeEntity.builder()
                .firstName("Pero")
                .lastName("Peric")
                .email("pero.peric@gmail.com")
                .password("1234")
                .role(EMPLOYEE)
                .build();

        EmployeeEntity employeeEntity3 = EmployeeEntity.builder()
                .firstName("Ivo")
                .lastName("Ivic")
                .email("ivo.ivic@gmail.com")
                .password("1234")
                .role(REVISER)
                .build();

        EmployeeEntity savedEmployee1Entity = employeeRepository.save(employeeEntity1);
        EmployeeEntity savedEmployee2Entity = employeeRepository.save(employeeEntity2);
        EmployeeEntity savedEmployee3Entity = employeeRepository.save(employeeEntity3);

        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();

        Assertions.assertThat(employeeEntityList).isNotNull();
        Assertions.assertThat(employeeEntityList.size()).isEqualTo(3);
    }

    @Test
    public void EmployeeRepository_FindById_ReturnsEmployee(){
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName("Ivan")
                .lastName("Horvat")
                .email("ivan.horvat@gmail.com")
                .password("1234")
                .role(DIRECTOR)
                .build();

        employeeRepository.save(employeeEntity);

        EmployeeEntity employeeEntityList = employeeRepository.findById(employeeEntity.getId()).get();

        Assertions.assertThat(employeeEntityList).isNotNull();
    }
}
