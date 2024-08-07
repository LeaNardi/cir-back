package com.cir.cirback.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import com.google.gson.Gson;

import jakarta.transaction.Transactional;

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

import com.cir.cirback.entities.Especialidad;
import com.cir.cirback.entities.Profesional;
import com.cir.cirback.entities.Titulo;
import com.cir.cirback.dtos.ExistsResponse;
import com.cir.cirback.dtos.ProfesionalDTO;
import com.cir.cirback.dtos.ProfesionalDTOSimp;
import com.cir.cirback.dtos.ProfesionalMapper;
import com.cir.cirback.repositories.EspecialidadRepository;
import com.cir.cirback.repositories.ProfesionalRepository;
import com.cir.cirback.repositories.TituloRepository;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/profesional")
public class ProfesionalController {
    @Autowired
    private ProfesionalRepository profesionalRepository;
    @Autowired
    private ProfesionalMapper profesionalMapper;
	@Autowired
    private EspecialidadRepository especialidadRepository;
	@Autowired
    private TituloRepository tituloRepository;

    @GetMapping(path = "/getall")
    public @ResponseBody ResponseEntity<Iterable<ProfesionalDTO>> getAllProfesionales() {
        return new ResponseEntity(
                profesionalRepository.findAll()
                        .stream()
                        .map(profesionalMapper::profesionalToProfesionalDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }
    
    @GetMapping(path = "/getallsimplified")
    public @ResponseBody ResponseEntity<Iterable<ProfesionalDTOSimp>> getAllProfesionalesSimplified() {
        return new ResponseEntity(
                profesionalRepository.findAll()
                        .stream()
                        .map(profesionalMapper::profesionalToProfesionalDtoSimplified)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/get/{dni}")
    public @ResponseBody ResponseEntity<?> getProfesional(@PathVariable(name = "dni") String dni) {

        Optional<Profesional> profesionalOptional = profesionalRepository.findByDni(dni);

        if (profesionalOptional.isPresent()) {
        	ProfesionalDTO profesionalDTO = profesionalMapper.profesionalToProfesionalDto(profesionalOptional.get());
            return new ResponseEntity<>(profesionalDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Profesional not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/getsimplified/{dni}")
    public @ResponseBody ResponseEntity<?> getProfesionalSimplified(@PathVariable(name = "dni") String dni) {

        Optional<Profesional> profesionalOptional = profesionalRepository.findByDni(dni);

        if (profesionalOptional.isPresent()) {
        	ProfesionalDTOSimp profesionalDTO = profesionalMapper.profesionalToProfesionalDtoSimplified(profesionalOptional.get());
            return new ResponseEntity<>(profesionalDTO, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Profesional not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<String> addNewProfesional(
            @RequestBody ProfesionalDTO profesionalDTO) {
        Profesional profesional = profesionalMapper.ProfesionalDtotoprofesional(profesionalDTO);
        profesionalRepository.save(profesional);

        String jsonMessage = new Gson().toJson("Profesional Saved");
        return new ResponseEntity(jsonMessage, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{dni}")
    @Transactional
    public @ResponseBody ResponseEntity<String> deleteProfesional(@PathVariable(name = "dni") String dni) {
    	Boolean exists = profesionalRepository.existsByDni(dni);
    	if (exists) {
    		profesionalRepository.deleteByDni(dni);
    		String jsonMessage = new Gson().toJson("Profesional Deleted");
            return new ResponseEntity(jsonMessage, HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Profesional No Existe");
            return new ResponseEntity(jsonMessage, HttpStatus.OK);
        }
    		
    }

    @PutMapping(path = "/update/{dni}")
    public @ResponseBody ResponseEntity<String> modifyProfesional(
            @PathVariable(name = "dni") String dni,
            @RequestBody ProfesionalDTO profesionalDTO) {
    	Gson gson = new Gson();
    	
        if (!profesionalRepository.existsByDni(dni)) {
            return new ResponseEntity(gson.toJson("Profesional does not exist"), HttpStatus.NOT_FOUND);
        }

        Profesional profesional = profesionalRepository.findByDni(dni).get();
        if (!profesional.getDni().equals(profesionalDTO.getDni())) {
            return new ResponseEntity(gson.toJson("Profesional DNI does not match"), HttpStatus.BAD_REQUEST);
        }

        
        profesional.setDni(profesionalDTO.getDni());
        profesional.setNombre(profesionalDTO.getNombre());
        profesional.setApellido(profesionalDTO.getApellido());
        profesional.setEmail(profesionalDTO.getEmail());
        profesional.setDireccion(profesionalDTO.getDireccion());
        profesional.setTelefono(profesionalDTO.getTelefono());
        Especialidad especialidad = especialidadRepository.findById(profesionalDTO.getEspecialidadId()).get();
        profesional.setEspecialidad(especialidad);
        profesional.setActivo(profesionalDTO.isActivo());
        profesional.setMotivobaja(profesionalDTO.getMotivobaja());
        profesional.setFechaIngreso(profesionalDTO.getFechaIngreso());
        Titulo titulo = tituloRepository.findById(profesionalDTO.getTituloId()).get();
        profesional.setTitulo(titulo);
        profesional.setFormacionesComplementarias(profesionalDTO.getFormacionesComplementarias());
        profesional.setPublicacionesRevistas(profesionalDTO.getPublicacionesRevistas());
        profesional.setPresentacionesCongresos(profesionalDTO.getPresentacionesCongresos());
        profesional.setExperienciaLaboral(profesionalDTO.getExperienciaLaboral());
        

        profesionalRepository.save(profesional);
        return new ResponseEntity(gson.toJson("Profesional updated"), HttpStatus.OK);
    }
    
    @PutMapping(path = "/disable/{dni}")
    public @ResponseBody ResponseEntity<String> disableProfesional(
            @PathVariable(name = "dni") String dni,
            @RequestBody String motivo_baja) {
    	Gson gson = new Gson();
    	
        if (!profesionalRepository.existsByDni(dni)) {
            return new ResponseEntity(gson.toJson("Profesional does not exist"), HttpStatus.NOT_FOUND);
        }

        Profesional profesional = profesionalRepository.findByDni(dni).get();
       
        profesional.setActivo(false);
        profesional.setMotivobaja(motivo_baja);

        profesionalRepository.save(profesional);
        return new ResponseEntity(gson.toJson("Profesional updated"), HttpStatus.OK);
    }
    
    
    @GetMapping("/exists/{dni}")
    public @ResponseBody ResponseEntity<?> existsProfesional(@PathVariable(name = "dni") String dni) {
    	ExistsResponse response = new ExistsResponse();
    	Profesional profesional;
        //Optional<Profesional> profesionalOptional = profesionalRepository.existsByDni(dni);
        Boolean exists = profesionalRepository.existsByDni(dni);
        response.setExists(exists);
        if (exists) {
        	profesional = profesionalRepository.findByDni(dni).get();
        	response.setActive(profesional.isActivo());
        } else {
        	response.setActive(false);
        }
        String jsonMessage = new Gson().toJson(response);
        return new ResponseEntity<>(jsonMessage, HttpStatus.OK);
    }
 
}

