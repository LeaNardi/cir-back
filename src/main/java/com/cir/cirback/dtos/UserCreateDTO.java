package com.cir.cirback.dtos;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateDTO {
    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String dni;
    private Set<Integer> roles_ids;
}
