package com.cir.cirback.dtos;

import com.cir.cirback.entities.Role;

public class RoleMapper {
	public RoleDTO roleToRoleDto(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		
		roleDTO.setId(role.getId());
		roleDTO.setRole(role.getRole());
		
		return roleDTO;
	}
	
}
