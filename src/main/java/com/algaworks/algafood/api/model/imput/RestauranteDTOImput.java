package com.algaworks.algafood.api.model.imput;

import com.algaworks.algafood.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
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

}

