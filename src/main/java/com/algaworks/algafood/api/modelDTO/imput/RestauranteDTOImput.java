package com.algaworks.algafood.api.modelDTO.imput;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteDTOImput {

    @NotBlank
    private String nome;

    @PositiveOrZero
    @NotNull
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaReferenciaIDImput cozinha;

    private boolean ativo;

    private boolean aberto;

    @Valid
    @NotNull
    private EnderecoDTOImput endereco;

}

