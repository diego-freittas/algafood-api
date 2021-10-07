package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.modelDTO.CidadeDTO;
import com.algaworks.algafood.api.modelDTO.UsuarioCadastroDTO;
import com.algaworks.algafood.api.modelDTO.UsuarioSemSenhaDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioCadastroDTO toUsuarioCadastroDTO(Usuario usuario) {

        return modelMapper.map(usuario, UsuarioCadastroDTO.class);
    }
    public UsuarioSemSenhaDTO toUsuarioSemSenhaDTO(Usuario usuario) {

        return modelMapper.map(usuario, UsuarioSemSenhaDTO.class);
    }

    public List<UsuarioSemSenhaDTO> toCollectionUsuarioSemSenhaDTO(List<Usuario> usuarios){
        return usuarios.stream()
                .map(usuario -> toUsuarioSemSenhaDTO(usuario))
                .collect(Collectors.toList());
    }

}
