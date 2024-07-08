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

import com.cir.cirback.dtos.ObraSocialDTO;
import com.cir.cirback.dtos.ObraSocialMapper;
import com.cir.cirback.entities.ObraSocial;
import com.cir.cirback.repositories.ObraSocialRepository;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path = "/api/obrasocial") // This means URL's start with /api (after Application path)
public class ObraSocialController {
    @Autowired
    private ObraSocialRepository obraSocialRepository;
    @Autowired
    private ObraSocialMapper obraSocialMapper;

    @GetMapping(path = "/getall")
    public @ResponseBody ResponseEntity<Iterable<ObraSocialDTO>> getAllObraSociales() {
        // This returns a JSON or XML with the users
        return new ResponseEntity(
        		obraSocialRepository.findAll()
                        .stream()
                        .map(obraSocialMapper::obraSocialToObraSocialDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<?> getObraSocial(@PathVariable(name = "id") int id) {
        Optional<ObraSocial> obraSocialOptional = obraSocialRepository.findById(id);

        if (obraSocialOptional.isPresent()) {
        	ObraSocialDTO obraSocialDTO = obraSocialMapper.obraSocialToObraSocialDto(obraSocialOptional.get());
            return new ResponseEntity<>(obraSocialDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("ObraSocial not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }
    
}