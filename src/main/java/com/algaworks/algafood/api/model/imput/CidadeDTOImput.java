package com.algaworks.algafood.api.model.imput;

import com.algaworks.algafood.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeDTOImput {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private Estado estado;
}
