package com.algaworks.algafood.api.modelDTO.imput;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioCadastroDTOImput {

    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;
}
