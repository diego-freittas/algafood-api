package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.modelDTO.imput.CidadeDTOImput;
import com.algaworks.algafood.api.modelDTO.imput.UsuarioCadastroDTOImput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDTOImputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioCadastroDTOImput usuarioCadastroDTOImput){
        return modelMapper.map(usuarioCadastroDTOImput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioCadastroDTOImput usuarioCadastroDTOImput,
                                   Usuario usuario){
        modelMapper.map(usuarioCadastroDTOImput,usuario);
    }
}
