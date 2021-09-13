package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
<<<<<<< HEAD
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
=======
>>>>>>> 4351c6a0cb1edfc08e16f4e6681d22d41c04964e

import com.algaworks.algafood.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Valid
<<<<<<< HEAD
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
=======
>>>>>>> 4351c6a0cb1edfc08e16f4e6681d22d41c04964e
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado estado;

}