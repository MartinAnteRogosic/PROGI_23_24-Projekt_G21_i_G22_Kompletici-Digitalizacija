package hr.fer.progi.backend.token;


import hr.fer.progi.backend.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue
    private Long tokenId;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

}
