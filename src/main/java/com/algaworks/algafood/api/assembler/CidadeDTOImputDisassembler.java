package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.modelDTO.imput.CidadeDTOImput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTOImputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeDTOImput cidadeDTOImput){
        return modelMapper.map(cidadeDTOImput, Cidade.class);
    }

    public void copyToDomainObject(CidadeDTOImput cidadeDTOImput,
                                   Cidade cidade){
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeDTOImput,cidade);
    }
}
