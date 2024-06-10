package com.cir.cirback.dtos;

import org.springframework.stereotype.Component;

import com.cir.cirback.entities.Role;

@Component
public class RoleMapper {
    public RoleDTO roleToRoleDto(Role role) {
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setRoleId(role.getRoleId());
        roleDTO.setRole(role.getRole());

        return roleDTO;
    }
    
    public Role roleCreateDtoToRole(RoleCreateDTO roleCreate) {
        Role role = new Role();

        role.setRole(roleCreate.getRole());

        return role;
    }

}
