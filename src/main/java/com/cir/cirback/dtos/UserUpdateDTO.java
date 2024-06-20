package com.cir.cirback.dtos;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDTO {
    private String username;
    private String email;
    private String name;
    private String surname;
    private String dni;
    private Integer role_id;
}
