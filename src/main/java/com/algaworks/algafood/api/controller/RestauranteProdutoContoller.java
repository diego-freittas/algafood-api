package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.ProdutoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<ProdutoDTO> listar (@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteSevice.buscarOuFalhar(restauranteId);
        return produtoDTOAssembler.toCollectionDTO(restaurante.getProdutos());
    }
    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        restauranteSevice.desassociarProduto(restauranteId,produtoId);
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        restauranteSevice.associarProduto(restauranteId,produtoId);
    }


}
