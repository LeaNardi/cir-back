package com.cir.cirback.dtos;

import org.springframework.stereotype.Component;

import com.cir.cirback.entities.Titulo;

@Component
public class TituloMapper {
    public TituloDTO tituloToTituloDto(Titulo titulo) {
    	TituloDTO tituloDTO = new TituloDTO();

    	tituloDTO.setTituloId(titulo.getTituloId());
    	tituloDTO.setTitulo(titulo.getTitulo());

        return tituloDTO;
    }

}
