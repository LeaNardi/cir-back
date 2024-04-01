package com.cir.cirback.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.entities.Role;
import com.cir.cirback.repositories.RoleRepository;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path="/api/role") // This means URL's start with /api (after Application path)
public class RoleController {
  @Autowired // This means to get the bean called userRepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private RoleRepository roleRepository;

  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String addNewRole (
		  @RequestParam String rolename
      ) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    Role role = new Role();
    role.setRole(rolename);
    roleRepository.save(role);
    return "Saved";
  }

  @GetMapping(path="/getall")
  public @ResponseBody Iterable<Role> getAllRoles() {
    // This returns a JSON or XML with the users
    return roleRepository.findAll();
  }
  
	@GetMapping("/get/{id}")
	public @ResponseBody Optional<Role> getRoler(@PathVariable(name = "id") int id) {
		return roleRepository.findById(id);
	}
  
	@DeleteMapping("/delete/{id}")
	public void deleteRole(@PathVariable(name = "id") int id) {
		roleRepository.deleteById(id);
	}
}