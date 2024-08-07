package com.cir.cirback.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.dtos.RoleDTO;
import com.cir.cirback.dtos.UserCreateDTO;
import com.cir.cirback.dtos.UserDTO;
import com.cir.cirback.dtos.UserMapper;
import com.cir.cirback.dtos.UserUpdateDTO;
import com.cir.cirback.entities.Role;
import com.cir.cirback.entities.User;
import com.cir.cirback.repositories.RoleRepository;
import com.cir.cirback.repositories.UserRepository;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path = "/api/user") // This means URL's start with /api (after Application path)
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "/getall")
    public @ResponseBody ResponseEntity<Iterable<UserDTO>> getAllUsers() {
        // This returns a JSON or XML with the users
        return new ResponseEntity(
                userRepository.findAll()
                        .stream()
                        .map(userMapper::userToUserDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<?> getUser(@PathVariable(name = "id") int id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserDTO userDTO = userMapper.userToUserDto(userOptional.get());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("User not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String> addNewUser(
            @RequestBody UserCreateDTO userCreate) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        User n = userMapper.userCreateDTOtoUser(userCreate);
        userRepository.save(n);

        //return new ResponseEntity("{\"Message\": \"User saved\"}", HttpStatus.OK);
        //String json = new Gson().toJson(userCreate);
        String jsonMessage = new Gson().toJson("User Saved");
        return new ResponseEntity(jsonMessage, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<String> deleteUser(@PathVariable(name = "id") int id) {
        userRepository.deleteById(id);
        //return new ResponseEntity("{\"Message\": \"User Deleted\"}", HttpStatus.OK);
        String jsonMessage = new Gson().toJson("User Deleted");
        return new ResponseEntity(jsonMessage, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}") // Map ONLY PUT Requests
    public @ResponseBody ResponseEntity<String> modifyUser(
            @PathVariable(name = "id") int id,
            @RequestBody UserDTO userUpdate) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
    	Gson gson = new Gson();
    	
        if (!userRepository.existsById(id)) {
            return new ResponseEntity(gson.toJson("User does not exist"), HttpStatus.NOT_FOUND);
        }

        User n = userRepository.findById(id).get();
        if (n.getUserId() != userUpdate.getUserId()) {
            return new ResponseEntity(gson.toJson("User Id does not match"), HttpStatus.BAD_REQUEST);
        }
        
        if (!n.getUsername().equals(userUpdate.getUsername())) {
            return new ResponseEntity(gson.toJson("Username does not match"), HttpStatus.BAD_REQUEST);
        }

        n.setEmail(userUpdate.getEmail());
        n.setName(userUpdate.getName());
        n.setSurname(userUpdate.getSurname());
        n.setDni(userUpdate.getDni());
        
//        Set<Role> roles = userUpdate
//                .getRoles_ids()
//                .stream()
//                .map(roleId -> roleRepository.findById(roleId).get())
//                .collect(Collectors.toSet());
//        n.setRoles(roles);
        Role role = roleRepository.findById(userUpdate.getRole_id()).get();
        n.setRole(role);

        userRepository.save(n);
        return new ResponseEntity(gson.toJson("User updated"), HttpStatus.OK);
    }
    
    
    @GetMapping("/myuser/{username}")
    public @ResponseBody ResponseEntity<?> getMyUser(@PathVariable(name = "username") String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserDTO userDTO = userMapper.userToUserDto(userOptional.get());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("User not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }
}