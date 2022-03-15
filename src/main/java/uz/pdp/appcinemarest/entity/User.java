package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.enums.Gender;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

// Zuhridin Bakhriddinov 3/14/2022 3:09 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String  userName;

    @Column(nullable = false)
    private String password;

    private Date dateOfBirth;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> role;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Permission> permission;

    private Gender gender;



}
