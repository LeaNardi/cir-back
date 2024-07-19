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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cir.cirback.entities.Turno;
import com.cir.cirback.entities.User;
import com.cir.cirback.dtos.ProfesionalDTO;
import com.cir.cirback.dtos.TurnoDTO;
import com.cir.cirback.dtos.TurnoMapper;
import com.cir.cirback.dtos.TurnosGenerateDTO;
import com.cir.cirback.dtos.UserDTO;
import com.cir.cirback.entities.Profesional;
import com.cir.cirback.repositories.ProfesionalRepository;
import com.cir.cirback.repositories.TurnoRepository;
import com.cir.cirback.repositories.UserRepository;
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
	@Autowired
    private UserRepository userRepository;

	
	@GetMapping("/getturno/{turnoId}")
    public @ResponseBody ResponseEntity<?> getTurnosPorId(@PathVariable(name = "turnoId") Integer turnoId) {
		Optional<Turno> turnoOptional = turnoRepository.findByTurnoId(turnoId);
		if (turnoOptional.isPresent()) {
        	TurnoDTO turnoDto = turnoMapper.turnoToTurnoDto(turnoOptional.get());
            return new ResponseEntity(turnoDto, HttpStatus.OK);	
        } else {
        	String jsonMessage = new Gson().toJson("Turno not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/getdisponibles/{dniprofesional}")
    public @ResponseBody ResponseEntity<?> getTurnosDisponibles(@PathVariable(name = "dniprofesional") String dniprofesional) {
		
		Optional<Profesional> profesionalOptional = profesionalRepository.findByDni(dniprofesional);
        if (profesionalOptional.isPresent()) {
        	Profesional profesional = profesionalOptional.get();
        	List<Turno> turnos = turnoRepository.findByProfesional(profesional);
        	List<Turno> turnos_disponibles = turnos.stream().filter(t -> t.getPaciente() == null && t.getFecha().isAfter(LocalDate.now().minusDays(1)) ).collect(Collectors.toList());
        	
        	return new ResponseEntity(
        			turnos_disponibles.stream().map(turnoMapper::turnoToTurnoDto).collect(Collectors.toList()),
                    HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Profesional not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
		
    }
	
	@GetMapping("/getocupados/{dniprofesional}")
    public @ResponseBody ResponseEntity<?> getTurnosParaProfesional(@PathVariable(name = "dniprofesional") String dniprofesional) {
		
		Optional<Profesional> profesionalOptional = profesionalRepository.findByDni(dniprofesional);
        if (profesionalOptional.isPresent()) {
        	Profesional profesional = profesionalOptional.get();
        	List<Turno> turnos = turnoRepository.findByProfesional(profesional);      	
        	List<Turno> turnos_futuros = turnos.stream().filter(t -> t.getPaciente() != null && t.getFecha().isAfter(LocalDate.now().minusDays(1)) ).collect(Collectors.toList());
        	
        	return new ResponseEntity(
        			turnos_futuros.size(),
                    HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("Profesional not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
		
    }
	
	@GetMapping("/getmisturnos/{id}")
    public @ResponseBody ResponseEntity<?> getMisTurnos(@PathVariable(name = "id") int id) {
		Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
        	List<Turno> turnos = turnoRepository.findByPaciente(userOptional.get());
        	List<Turno> turnos_futuros = turnos.stream().filter(t -> t.getFecha().isAfter(LocalDate.now().minusDays(1)) ).collect(Collectors.toList());
        	
            return new ResponseEntity<>(
            		turnos_futuros.stream().map(turnoMapper::turnoToTurnoDto).collect(Collectors.toList()),
            		HttpStatus.OK);
        } else {
        	String jsonMessage = new Gson().toJson("User not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
		
    }
	
	@PutMapping(path = "/reservarturno")
	public @ResponseBody ResponseEntity<?> ReservarTurno(
            @RequestBody TurnoDTO turnoRequestDTO){
		Optional<Turno> turnoOptional = turnoRepository.findByTurnoId(turnoRequestDTO.getTurnoId());
		
		if (turnoOptional.isPresent()) {
        	Turno turno = turnoOptional.get();
        	Turno turnoReq = turnoMapper.turnoDtoToTurno(turnoRequestDTO);
        	
        	turno.setPaciente(turnoReq.getPaciente());
        	turno.setObraSocial(turnoReq.getObraSocial());
        	
        	turnoRepository.save(turno);
        	
        	String jsonMessage = new Gson().toJson("Turno Saved");
            return new ResponseEntity(jsonMessage, HttpStatus.OK);
        	
        	
        } else {
        	String jsonMessage = new Gson().toJson("Turno not found");
            return new ResponseEntity<>(jsonMessage, HttpStatus.NOT_FOUND);
        }
	}
	
	@PutMapping(path = "/cancelarturno")
	public @ResponseBody ResponseEntity<?> cancelarTurno(
            @RequestBody TurnoDTO turnoRequestDTO){
		Optional<Turno> turnoOptional = turnoRepository.findByTurnoId(turnoRequestDTO.getTurnoId());
		
		if (turnoOptional.isPresent()) {
        	Turno turno = turnoOptional.get();
        	//Turno turnoReq = turnoMapper.turnoDtoToTurno(turnoRequestDTO);
        	
        	turno.setPaciente(null);
        	turno.setObraSocial(null);
        	
        	turnoRepository.save(turno);
        	
        	String jsonMessage = new Gson().toJson("Turno Cancelled");
            return new ResponseEntity(jsonMessage, HttpStatus.OK);
        	
        	
        } else {
        	String jsonMessage = new Gson().toJson("Turno not found");
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
        
        Optional<Profesional> profesionalOptional = profesionalRepository.findByDni(turnosGenerate.getProfesionalDni());

        if (!profesionalOptional.isPresent()) {
        	String jsonMessage = new Gson().toJson("No Existe el Profesional");
            return new ResponseEntity(jsonMessage, HttpStatus.NOT_FOUND);
        }
        
        if (!profesionalOptional.get().isActivo()) {
        	String jsonMessage = new Gson().toJson("El profesional se encuentra deshabilitado");
            return new ResponseEntity(jsonMessage, HttpStatus.BAD_REQUEST);
        }
        
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
