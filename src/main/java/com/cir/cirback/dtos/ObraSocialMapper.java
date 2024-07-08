package com.cir.cirback.dtos;

import org.springframework.stereotype.Component;

import com.cir.cirback.entities.ObraSocial;

@Component
public class ObraSocialMapper {
    public ObraSocialDTO obraSocialToObraSocialDto(ObraSocial obraSocial) {
    	ObraSocialDTO obraSocialDTO = new ObraSocialDTO();

    	obraSocialDTO.setObraSocialId(obraSocial.getObraSocialId());
    	obraSocialDTO.setObraSocial(obraSocial.getObraSocial());

        return obraSocialDTO;
    }

}
