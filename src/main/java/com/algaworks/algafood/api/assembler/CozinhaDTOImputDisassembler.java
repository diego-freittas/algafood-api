package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.imput.CozinhaDTOImput;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTOImputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaDTOImput cozinhaDTOImput){
        return modelMapper.map(cozinhaDTOImput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaDTOImput cozinhaDTOImput,
                                   Cozinha cozinha){
        modelMapper.map(cozinhaDTOImput,cozinha);
    }
}
