package com.algaworks.algafood.api.modelDTO.imput;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class FormaPagamentoDTOImput {

    @NotBlank
    private String descricao;

}
