package hr.fer.progi.backend.employee;

import hr.fer.progi.backend.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String employeeName;

    private String employeeSurname;

    private String employeePassword;

    private String employeeEmail;

    @Enumerated(EnumType.STRING)
    private Role employeeRole;

    @OneToMany(mappedBy = "employee")
    private List<Token> employeeTokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return employeeRole.getAuthorities();
    }

    @Override
    public String getPassword() {
        return employeePassword;
    }

    @Override
    public String getUsername() {
        return employeeEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}