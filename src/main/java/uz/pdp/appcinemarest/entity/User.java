package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appcinemarest.entity.enums.Gender;
import uz.pdp.appcinemarest.entity.enums.Role_enum;

import javax.persistence.*;
import java.util.*;

// Zuhridin Bakhriddinov 3/14/2022 3:09 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Date dateOfBirth;


    @Enumerated(EnumType.STRING)
    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Permission> permission;

    private Gender gender;
    private String emailCode;

    private boolean enabled;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;


    @Transactional
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        Set<GrantedAuthority> grantedAuthorities=new HashSet<>();

            for (Permission role1Permission : this.role.getPermissions()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role1Permission.getName()));

        }



        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String fullName, String email, String password, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
