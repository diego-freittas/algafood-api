package com.algaworks.algafood.api.model.imput;

import com.algaworks.algafood.Groups;
import com.algaworks.algafood.domain.model.Estado;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Setter
@Getter
public class CidadeDTOImput {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private Estado estado;
}
