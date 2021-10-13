package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoContoller {

//
//    @Autowired
//    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
//
//    @Autowired
//    private RestauranteSevice restauranteSevice;
//
//    @GetMapping
//    public List<FormaPagamentoDTO> listar( @PathVariable Long restauranteId) {
//        Restaurante restaurante = restauranteSevice.buscarOuFalhar(restauranteId);
//        return formaPagamentoDTOAssembler.toCollectionDTO(restaurante.getFormaPagamentos());
//    }

//    @DeleteMapping("/{produtoId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long produtoId){
//        restauranteSevice.desassociarFormaPagamento(restauranteId,produtoId);
//    }
//
//    @PutMapping("/{produtoId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void associar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
//        restauranteSevice.associarFormaPagamento(restauranteId, produtoId);
//    }

}
