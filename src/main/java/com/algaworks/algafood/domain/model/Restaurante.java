package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome",descricaoObrigatoria="Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    @Column(nullable = false)
    private String nome;

    //@PositiveOrZero
    //@NotNull
    //@TaxaFrete
    //@Multiplo(numero = 0)
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    //@Valid
    //@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    //@NotNull(groups = Groups.CozinhaId.class)
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private boolean ativo = Boolean.TRUE;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
    joinColumns = @JoinColumn(name = "restaurante_id"),
    inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;


    @OneToMany(mappedBy = "restaurante")
    private Set<Produto> produtos = new HashSet<>();

    public void ativar(){
        this.setAtivo(true);
    }
    public void inativar(){
        this.setAtivo(false);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento){
        getFormaPagamentos().add(formaPagamento);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento){
        getFormaPagamentos().remove(formaPagamento);
    }

    public void adicionarProduto(Produto produto){
        getProdutos().add(produto);
    }

    public void removerProduto(Produto produto){
        System.out.println("Antes" + this.getProdutos().size());
        boolean isRemovido = getProdutos().remove(produto);
        System.out.println("Depois" + this.getProdutos().size());
        System.out.println(produto.getId());
        System.out.println(isRemovido);
    }

}