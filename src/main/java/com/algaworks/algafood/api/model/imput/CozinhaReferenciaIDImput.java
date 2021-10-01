package com.algaworks.algafood.api.model.imput;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CozinhaReferenciaIDImput {

    @NotNull
    private Long id;
}
