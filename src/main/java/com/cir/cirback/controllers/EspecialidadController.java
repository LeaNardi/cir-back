package com.cir.cirback.controllers;

import java.util.Optional;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.dtos.EspecialidadDTO;
import com.cir.cirback.dtos.EspecialidadMapper;
import com.cir.cirback.entities.Especialidad;
import com.cir.cirback.repositories.EspecialidadRepository;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path = "/api/especialidad") // This means URL's start with /api (after Application path)
public class EspecialidadController {
    @Autowired
    private EspecialidadRepository especialidadRepository;
    @Autowired
    private EspecialidadMapper especialidadMapper;

    @GetMapping(path = "/getall")
    public @ResponseBody ResponseEntity<Iterable<EspecialidadDTO>> getAllEspecialidades() {
        // This returns a JSON or XML with the users
        return new ResponseEntity(
        		especialidadRepository.findAll()
                        .stream()
                        .map(especialidadMapper::especialidadToEspecialidadDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<?> getEspecialidad(@PathVariable(name = "id") int id) {
        Optional<Especialidad> especialidadOptional = especialidadRepository.findById(id);

        if (especialidadOptional.isPresent()) {
        	EspecialidadDTO especialidadDTO = especialidadMapper.especialidadToEspecialidadDto(especialidadOptional.get());
            return new ResponseEntity<>(especialidadDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Especialidad not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }
    
}