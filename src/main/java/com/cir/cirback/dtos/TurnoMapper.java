package com.cir.cirback.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cir.cirback.entities.ObraSocial;
import com.cir.cirback.entities.Profesional;
import com.cir.cirback.entities.User;
import com.cir.cirback.entities.Turno;
import com.cir.cirback.repositories.ObraSocialRepository;
import com.cir.cirback.repositories.ProfesionalRepository;
import com.cir.cirback.repositories.UserRepository;

@Component
public class TurnoMapper {
	@Autowired
    private ProfesionalRepository profesionalRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private ObraSocialRepository obraSocialRepository;
	
	public TurnoDTO turnoToTurnoDto(Turno turno) {
		TurnoDTO turnoDto = new TurnoDTO();
		
		turnoDto.setTurnoId(turno.getTurnoId());
		turnoDto.setProfesionalDni(turno.getProfesional().getDni());
		turnoDto.setFecha(turno.getFecha());
		turnoDto.setHora(turno.getHora());

	    if (turno.getObraSocial() != null) {
	        turnoDto.setObraSocialId(turno.getObraSocial().getObraSocialId());
	    } else {
	        turnoDto.setObraSocialId(0);
	    }
	    
	    if (turno.getPaciente() != null) {
	        turnoDto.setPacienteId(turno.getPaciente().getUserId());
	    } else {
	        turnoDto.setPacienteId(0);
	    }
		
		return turnoDto;
	}
	
	public Turno turnoDtoToTurno(TurnoDTO turnoDto) {
		Turno turno = new Turno();
		
		//turno.setTurnoId(turnoDto.getTurnoId());
		Profesional profesional = profesionalRepository.findByDni(turnoDto.getProfesionalDni()).get();
		turno.setProfesional(profesional);
		turno.setFecha(turnoDto.getFecha());
		turno.setHora(turnoDto.getHora());

	    if (turnoDto.getObraSocialId() != 0) {
			ObraSocial obraSocial = obraSocialRepository.findById(turnoDto.getObraSocialId()).get();
        	turno.setObraSocial(obraSocial);
	    } else {
	        turno.setObraSocial(null);
	    }
	    
	    if (turnoDto.getPacienteId() != 0) {
			User user = userRepository.findById(turnoDto.getPacienteId()).get();
        	turno.setPaciente(user);
	    } else {
	        turno.setPaciente(null);
	    }
		
		return turno;
	}
}
