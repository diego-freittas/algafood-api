package com.algaworks.algafood.api.modelDTO.imput;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioCadastroSenhaDTOImput extends UsuarioCadastroDTOImput{

    @NotBlank
    private String senha;
}
