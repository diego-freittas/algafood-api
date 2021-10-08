package com.algaworks.algafood.api.modelDTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class UsuarioCadastroDTO extends UsuarioSemSenhaDTO{

    @NotBlank
    private String senha;
}
