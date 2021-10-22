package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOImputDisassembler;
import com.algaworks.algafood.api.modelDTO.RestauranteDTO;
import com.algaworks.algafood.api.modelDTO.imput.RestauranteDTOImput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteContoller {

    @Autowired
    private RestauranteSevice restauranteSevice;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteDTOAssembler restauranteDTOAssembler;

    @Autowired
    private RestauranteDTOImputDisassembler restauranteDTOImputDisassembler;


    //@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }) // "produces" serve para :Esta metodo só produz o formato especifico de conteudo
    @GetMapping
    public List<RestauranteDTO> listar() {

        return restauranteDTOAssembler.toCollectionDTO(restauranteSevice.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteSevice.buscarOuFalhar(id);
        return restauranteDTOAssembler.toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOImput restauranteDTOImput) {
        try {
            Restaurante restaurante = restauranteDTOImputDisassembler.toDomainObject(restauranteDTOImput);
            return restauranteDTOAssembler.toDTO(restauranteSevice.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                 @RequestBody  @Valid RestauranteDTOImput restauranteDTOImput) {
        try {
            Restaurante restaurante = restauranteDTOImputDisassembler.toDomainObject(restauranteDTOImput);

            Restaurante restauranteAtual = restauranteSevice.buscarOuFalhar(restauranteId);
            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return restauranteDTOAssembler.toDTO(restauranteSevice.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        restauranteSevice.excluir(id);
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechamento(@PathVariable Long id){
        restauranteSevice.fechar(id);
    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long id){
        restauranteSevice.abrir(id);
    }

}
