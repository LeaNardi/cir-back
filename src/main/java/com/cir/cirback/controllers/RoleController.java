package com.cir.cirback.controllers;

import java.util.Optional;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.dtos.RoleCreateDTO;
import com.cir.cirback.dtos.RoleDTO;
import com.cir.cirback.dtos.RoleMapper;
import com.cir.cirback.entities.Role;
import com.cir.cirback.repositories.RoleRepository;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path = "/api/role") // This means URL's start with /api (after Application path)
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    @GetMapping(path = "/getall")
    public @ResponseBody ResponseEntity<Iterable<RoleDTO>> getAllRoles() {
        // This returns a JSON or XML with the users
        return new ResponseEntity(
                roleRepository.findAll()
                        .stream()
                        .map(roleMapper::roleToRoleDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<?> getRole(@PathVariable(name = "id") int id) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isPresent()) {
            RoleDTO roleDTO = roleMapper.roleToRoleDto(roleOptional.get());
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Role not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String> addNewRole(
    		@RequestBody RoleCreateDTO roleCreate) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Role role = roleMapper.roleCreateDtoToRole(roleCreate) ;
        roleRepository.save(role);
        String jsonMessage = new Gson().toJson("Role saved");
        return new ResponseEntity(jsonMessage, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<String> deleteRole(@PathVariable(name = "id") int id) {
        roleRepository.deleteById(id);
        String jsonMessage = new Gson().toJson("Role Deleted");
        return new ResponseEntity(jsonMessage, HttpStatus.OK);
    }
}