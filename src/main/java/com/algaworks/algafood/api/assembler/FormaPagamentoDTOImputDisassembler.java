package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.modelDTO.imput.FormaPagamentoDTOImput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTOImputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoDTOImput formaPagamentoDTOImput){
        return modelMapper.map(formaPagamentoDTOImput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoDTOImput formaPagamentoDTOImput,
                                   FormaPagamento formaPagamento){
        modelMapper.map(formaPagamentoDTOImput,formaPagamento);
    }
}
