package com.cir.cirback.dtos;

import com.cir.cirback.entities.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    private String role;
}
