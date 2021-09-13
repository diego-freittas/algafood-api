package com.algaworks.algafood.domain.model;

<<<<<<< HEAD
import com.algaworks.algafood.Groups;
=======
import com.algaworks.algafood.core.validation.Groups;
>>>>>>> 4351c6a0cb1edfc08e16f4e6681d22d41c04964e
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

//@JsonRootName("cozinha") //customizar  a tag XML
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @EqualsAndHashCode.Include
<<<<<<< HEAD
    @NotNull(groups = Groups.CozinhaId.class)
=======
>>>>>>> 4351c6a0cb1edfc08e16f4e6681d22d41c04964e
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //@JsonIgnore Ignora a propriedade na representação devolvida pela API
    // @JsonProperty(value = "titulo")//Mudando o nome do recurso na representação devolvida pela API
    @NotBlank
    @Column(nullable = false)
    private String nome;

    // uma Cozainha tem muitos Restaurantes
    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();

}
