package com.algaworks.algafood.api.modelDTO.imput;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
@Getter
@Setter
public class ProdutoDTOImput {


    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @PositiveOrZero
    private BigDecimal preco;

    @NotNull
    private Boolean ativo;
}
