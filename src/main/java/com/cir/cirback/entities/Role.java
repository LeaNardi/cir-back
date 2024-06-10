package com.cir.cirback.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // This tells Hibernate to make a table out of this class
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String role;
    // @ManyToMany(mappedBy = "roles")
    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "user_role",
    // joinColumns = @JoinColumn(name = "role_id"),
    // inverseJoinColumns = @JoinColumn(name = "user_id"))
    // @JsonBackReference
    // private Set<User> users = new HashSet<>();
}