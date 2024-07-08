package com.cir.cirback.controllers;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.entities.Turno;
import com.cir.cirback.dtos.ProfesionalDTO;
import com.cir.cirback.dtos.TurnoDTO;
import com.cir.cirback.dtos.TurnoMapper;
import com.cir.cirback.dtos.TurnosGenerateDTO;
import com.cir.cirback.entities.Profesional;
import com.cir.cirback.repositories.ProfesionalRepository;
import com.cir.cirback.repositories.TurnoRepository;
import com.google.gson.Gson;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path = "/api/turnos") // This means URL's start with /api (after Application path)
public class TurnoController {
	@Autowired
	private TurnoRepository turnoRepository;
	@Autowired
    private ProfesionalRepository profesionalRepository;
	@Autowired
    private TurnoMapper turnoMapper;
	
	@GetMapping("/get/{dniprofesional}")
    public @ResponseBody ResponseEntity<?> getTurnosParaProfesional(@PathVariable(name = "dniprofesional") String dniprofesional,
    		@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
		
		Optional<Profesional> profesionalOptional = profesionalRepository.findByDni(dniprofesional);
        if (profesionalOptional.isPresent()) {
        	Profesional profesional = profesionalOptional.get();
        	List<Turno> turnos = turnoRepository.findByProfesionalAndFecha(profesional, fecha);      	
        	
        	return new ResponseEntity(
    				turnos.stream().map(turnoMapper::turnoToTurnoDto).collect(Collectors.toList()),
                    HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Profesional not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
		
    }
	
    @PostMapping(path = "/generate") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String> generateTurnos(
            @RequestBody TurnosGenerateDTO turnosGenerate) {
        boolean lugar = false;
		LocalTime hora;
        TurnoDTO turnoDTO;
        Turno turno;
		if (LocalTime.parse(turnosGenerate.getAtencionInicio()).isBefore(LocalTime.parse(turnosGenerate.getAtencionFin()))) {
			lugar = true;
			hora = LocalTime.parse(turnosGenerate.getAtencionInicio());
			//System.out.println("Turnos para la fecha: " + fecha);
			
			while (lugar) {
				//System.out.println(turno);
                turnoDTO = new TurnoDTO();

                turnoDTO.setProfesionalDni(turnosGenerate.getProfesionalDni());
                turnoDTO.setFecha(turnosGenerate.getFecha());
                turnoDTO.setHora(hora);
                turnoDTO.setObraSocialId(0);
                turnoDTO.setPacienteId(0);
                
                turno = turnoMapper.turnoDtoToTurno(turnoDTO);
                turnoRepository.save(turno);
				
				hora = hora.plusMinutes(turnosGenerate.getDuracion());
				if (!hora.isBefore(LocalTime.parse(turnosGenerate.getAtencionFin()))) {
					lugar = false;
				}
			}
			
            String jsonMessage = new Gson().toJson("Turnos Saved");
            return new ResponseEntity(jsonMessage, HttpStatus.OK);
		}
        String jsonMessage = new Gson().toJson("No Turnos to Generate");
        return new ResponseEntity(jsonMessage, HttpStatus.OK);
    }

}
