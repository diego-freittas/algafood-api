package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.imput.EstadoDTOImput;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDTOImputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoDTOImput estadoDTOImput){
        return modelMapper.map(estadoDTOImput, Estado.class);
    }

    public void copyToDomainObject(EstadoDTOImput estadoDTOImput,
                                   Estado estado){
        modelMapper.map(estadoDTOImput,estado);
    }
}
