package hr.fer.progi.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.progi.backend.TestDataUtil;
import hr.fer.progi.backend.dto.EmployeeDto;
import hr.fer.progi.backend.dto.LoginDto;
import hr.fer.progi.backend.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, AuthenticationServiceImpl authenticationService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authenticationService = authenticationService;
    }

    @Test
    public void testThatRegisterSuccessfullyReturnsHttp200Ok() throws Exception {
        EmployeeDto testEmployee = TestDataUtil.createTestEmployeeDto();
        String employeeJSON = objectMapper.writeValueAsString(testEmployee);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/authenticate/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );


    }

    @Test
    public void testThatRegisterThrowsRegistrationException() throws Exception {
        EmployeeDto testEmployee = TestDataUtil.createTestEmployeeDtoExistingEmail();
        String employeeJSON = objectMapper.writeValueAsString(testEmployee);


        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/authenticate/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJSON)

        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.content().string("Email '" + testEmployee.getEmail() + "' is already taken.")
        );

    }

    @Test
    public void testThatLoginSuccessfullyReturnsLoggedInUser() throws Exception {

        EmployeeDto testEmployee = TestDataUtil.createTestEmployeeDto();

        authenticationService.register(testEmployee);

        LoginDto testLogin = TestDataUtil.createTestLoginDto();

        String loginJSON = objectMapper.writeValueAsString(testLogin);
        System.out.println(loginJSON);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/authenticate/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value("Pero")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value("Peric")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.role").value("EMPLOYEE")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.accessToken").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tokenType").value("Bearer ")
        );

    }
}
