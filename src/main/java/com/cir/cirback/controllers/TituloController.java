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

import com.cir.cirback.dtos.TituloDTO;
import com.cir.cirback.dtos.TituloMapper;
import com.cir.cirback.entities.Titulo;
import com.cir.cirback.repositories.TituloRepository;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path = "/api/titulo") // This means URL's start with /api (after Application path)
public class TituloController {
    @Autowired
    private TituloRepository tituloRepository;
    @Autowired
    private TituloMapper tituloMapper;

    @GetMapping(path = "/getall")
    public @ResponseBody ResponseEntity<Iterable<TituloDTO>> getAllTitulos() {
        // This returns a JSON or XML with the users
        return new ResponseEntity(
        		tituloRepository.findAll()
                        .stream()
                        .map(tituloMapper::tituloToTituloDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<?> getTitulo(@PathVariable(name = "id") int id) {
        Optional<Titulo> tituloOptional = tituloRepository.findById(id);

        if (tituloOptional.isPresent()) {
        	TituloDTO tituloDTO = tituloMapper.tituloToTituloDto(tituloOptional.get());
            return new ResponseEntity<>(tituloDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Titulo not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }
    
}