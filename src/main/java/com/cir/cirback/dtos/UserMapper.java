package com.cir.cirback.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cir.cirback.entities.User;
import com.cir.cirback.repositories.RoleRepository;
import com.cir.cirback.entities.Role;

@Component
public class UserMapper {
	@Autowired
    private RoleRepository roleRepository;
	
    public UserDTO userToUserDto(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setDni(user.getDni());
        Set<Integer> roles_ids = user
                .getRoles()
                .stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        userDTO.setRoles_ids(roles_ids);

        return userDTO;
    }

    public User userCreateDTOtoUser(UserCreateDTO userCreate) {
    	User user = new User();
    	
        user.setUsername(userCreate.getUsername());
        user.setEmail(userCreate.getEmail());
        user.setPassword(userCreate.getPassword());
        user.setName(userCreate.getName());
        user.setSurname(userCreate.getSurname());
        user.setDni(userCreate.getDni());
        Set<Role> roles = userCreate
                .getRoles_ids()
                .stream()
                .map(roleId -> roleRepository.findById(roleId).get())
                .collect(Collectors.toSet());
        user.setRoles(roles);    	
    	
    	return user;
    }
}
