package com.cir.cirback.controllers;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.dtos.ProfesionalDTO;
import com.cir.cirback.entities.Profesional;
import com.cir.cirback.repositories.TurnoRepository;
import com.google.gson.Gson;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path = "/api/turnos") // This means URL's start with /api (after Application path)
public class TurnoController {
	@Autowired
	private TurnoRepository turnoRepository;
	
	@GetMapping("/get/{dniprofesional}")
    public @ResponseBody ResponseEntity<?> getTurnosParaProfesional(@PathVariable(name = "dniprofesional") String dniprofesional) {
		
		return new ResponseEntity(
				turnoRepository.findByDniProfesional(dniprofesional),
                HttpStatus.OK);

        
    }
}
