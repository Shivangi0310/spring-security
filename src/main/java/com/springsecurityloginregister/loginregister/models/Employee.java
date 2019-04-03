package com.springsecurityloginregister.loginregister.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Column(name = "employee_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
//    @NotNull
    @NotEmpty(message = "*Please provide a name")
    private String name;

    @Column(name = "email")
//    @NotNull
    @Email(message = "*Please provide a valid email")
    @NotEmpty(message = "*Please provide an email.")
    private String email;

    @Column(name = "password")
    @NotNull
    @NotEmpty(message = "Please provide a password.")
//    @Length(min = 5, max = 10, message = "*Your password must have at least 5 characters")
    private String password;

    @Column(name = "roles")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "active")
    private int active;



}
