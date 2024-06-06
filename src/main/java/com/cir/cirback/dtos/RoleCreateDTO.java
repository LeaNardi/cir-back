package com.cir.cirback.dtos;

import com.cir.cirback.entities.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleCreateDTO {
    private String role;
}
