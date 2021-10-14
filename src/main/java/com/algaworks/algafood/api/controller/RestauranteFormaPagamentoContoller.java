package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.api.modelDTO.RestauranteDTO;
import com.algaworks.algafood.api.modelDTO.imput.RestauranteDTOImput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoContoller {


    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @Autowired
    private RestauranteSevice restauranteSevice;

    @GetMapping
    public List<FormaPagamentoDTO> listar( @PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteSevice.buscarOuFalhar(restauranteId);
        return formaPagamentoDTOAssembler.toCollectionDTO(restaurante.getFormaPagamentos());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteSevice.desassociarFormaPagamento(restauranteId,formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteSevice.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

}
