package hr.fer.progi.backend.entity;
import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "Statistic")
public class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private EmployeeEntity employeeEntity;

    @Column(name = "timestamp_login", nullable = false)
    private Timestamp timestampLogin;

    @Column(name = "timestamp_logout")
    private Timestamp timestampLogout;


    public LoginEntity() {

    }
}
