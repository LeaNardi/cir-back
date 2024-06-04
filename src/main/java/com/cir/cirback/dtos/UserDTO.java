package com.cir.cirback.dtos;

import java.util.HashSet;
import java.util.Set;

import com.cir.cirback.entities.Role;
import com.cir.cirback.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	  private Integer id;
	  private String username;
	  private String email;
	  private String name;
	  private String surname;
	  private String dni;
	  private Set<Integer> roles;
}
