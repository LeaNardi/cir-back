package com.cir.cirback.dtos;

import org.springframework.stereotype.Component;

import com.cir.cirback.entities.Especialidad;

@Component
public class EspecialidadMapper {
    public EspecialidadDTO especialidadToEspecialidadDto(Especialidad especialidad) {
    	EspecialidadDTO especialidadDTO = new EspecialidadDTO();

    	especialidadDTO.setEspecialidadId(especialidad.getEspecialidadId());
    	especialidadDTO.setEspecialidad(especialidad.getEspecialidad());

        return especialidadDTO;
    }

}
