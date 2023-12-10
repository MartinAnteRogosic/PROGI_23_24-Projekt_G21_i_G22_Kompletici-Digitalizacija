package hr.fer.progi.backend.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            select t from Token t
                inner join Employee e
                on t.employee.employeeId = e.employeeId
            where e.employeeId = :employeeId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokensByEmployee(Long employeeId);

    Optional<Token> findByToken(String token);

}
