package com.cir.cirback.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cir.cirback.entities.User;
import com.cir.cirback.entities.Role;

@Component
public class UserMapper {
    public UserDTO userToUserDto(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setDni(user.getDni());
        Set<Integer> roles = user
                .getRoles()
                .stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        userDTO.setRoles(roles);

        return userDTO;
    }

    // public User toUser(UserDTO userDTO) {
    // return new User(userDTO.getName(), userDTO.getPassword(), new ArrayList<>());
    // }
}
