package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.ProdutoDTO;
import com.algaworks.algafood.api.modelDTO.imput.ProdutoDTOImput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoContoller {

    @Autowired
    private ProdutoDTOAssembler produtoDTOAssembler;

    @Autowired
    private ProdutoDTOImputDisassembler produtoDTOImputDisassembler;

    @Autowired
    private RestauranteSevice restauranteSevice;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<ProdutoDTO> listar (@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteSevice.buscarOuFalhar(restauranteId);
        return produtoDTOAssembler.toCollectionDTO(restaurante.getProdutos());
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscarPorID (@PathVariable Long restauranteId,@PathVariable Long produtoId){

        return produtoDTOAssembler.toDTO(produtoService.buscarOuFalhar(restauranteId,produtoId));

    }
    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        restauranteSevice.deletarUmProduto(restauranteId,produtoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoDTOImput produtoInput) {
        Restaurante restaurante = restauranteSevice.buscarOuFalhar(restauranteId);
        Produto produto = produtoDTOImputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = produtoService.salvar(produto);
        return produtoDTOAssembler.toDTO(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoDTOImput produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId,produtoId);
        produtoDTOImputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        return produtoDTOAssembler.toDTO(produtoAtual);
    }

}
