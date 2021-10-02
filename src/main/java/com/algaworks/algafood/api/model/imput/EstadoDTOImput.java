package com.algaworks.algafood.api.model.imput;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Setter
@Getter
public class EstadoDTOImput {

    @NotBlank
    private String nome;
}
