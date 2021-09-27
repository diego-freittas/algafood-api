package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

//@JsonRootName("cozinha") //customizar  a tag XML
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @EqualsAndHashCode.Include
    @NotNull(groups = Groups.CozinhaId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //@JsonIgnore Ignora a propriedade na representação devolvida pela API
    // @JsonProperty(value = "titulo")//Mudando o nome do recurso na representação devolvida pela API
    @NotBlank
    @Column(nullable = false)
    private String nome;

    // uma Cozainha tem muitos Restaurantes
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();

}
