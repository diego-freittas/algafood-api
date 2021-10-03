package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class FormaPagamentoDTO {

    private Long id;
    private String descricao;
}
