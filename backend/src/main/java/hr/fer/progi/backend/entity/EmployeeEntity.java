package hr.fer.progi.backend.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "uploadEmployee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhotoEntity> scannedPhotos;

    @OneToMany(mappedBy = "validationEmployee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentEntity> validatedDocuments;

    @OneToMany(mappedBy = "scanEmployee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentEntity> scannedDocuments;
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