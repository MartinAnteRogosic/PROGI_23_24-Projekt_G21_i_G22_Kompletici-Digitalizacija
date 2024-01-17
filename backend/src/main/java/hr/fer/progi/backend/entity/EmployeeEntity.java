package hr.fer.progi.backend.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class EmployeeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "uploadEmployee", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PhotoEntity> scannedPhotos;

    @OneToMany(mappedBy = "verificationEmployee", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DocumentEntity> revisedDocuments;

    @OneToMany(mappedBy = "scanEmployee", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DocumentEntity> scannedDocuments;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<LoginLogOutRecordEntity> loginLogOutRecord;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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