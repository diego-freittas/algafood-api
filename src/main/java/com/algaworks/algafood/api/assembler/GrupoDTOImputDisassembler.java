package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.modelDTO.imput.CidadeDTOImput;
import com.algaworks.algafood.api.modelDTO.imput.GrupoDTOImput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTOImputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoDTOImput grupoDTOImput){
        return modelMapper.map(grupoDTOImput, Grupo.class);
    }

    public void copyToDomainObject(GrupoDTOImput grupoDTOImput,
                                   Grupo grupo){
        modelMapper.map(grupoDTOImput,grupo);
    }
}
