package com.cir.cirback.auth;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.dtos.UserDTO;
import com.cir.cirback.dtos.UserMapper;
import com.cir.cirback.entities.User;
import com.cir.cirback.repositories.RoleRepository;
import com.cir.cirback.repositories.UserRepository;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/authentication")
public class AuthController {
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private JwtUtil jwtUtil;

   
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
    		Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
    		User user;
    		
    		if (userOptional.isPresent()) {
    			user = userOptional.get();
            } else {
            	return new ResponseEntity("Incorrect username or password", HttpStatus.UNAUTHORIZED);
            }
        	
        	if(!(user.getPassword().equals(request.getPassword()))) {
        		return new ResponseEntity("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        	};
        	
            final String jwt = jwtUtil.generateToken(user.getUsername());

            return new ResponseEntity(new AuthenticationResponse(jwt), HttpStatus.OK);
    

    }
}
