package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.modelDTO.imput.ProdutoDTOImput;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTOImputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoDTOImput produtoDTOImput){
        return modelMapper.map(produtoDTOImput, Produto.class);
    }

    public void copyToDomainObject(ProdutoDTOImput produtoDTOImput,
                                   Produto produto){
        modelMapper.map(produtoDTOImput,produto);
    }
}
