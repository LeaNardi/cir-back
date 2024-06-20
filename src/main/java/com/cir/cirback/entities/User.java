package com.cir.cirback.entities;

import java.util.HashSet;
import java.util.Set;

import com.cir.cirback.entities.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String dni;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    // @JsonManagedReference
//    private Set<Role> roles = new HashSet<>();
}