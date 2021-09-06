package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteContoller {

    @Autowired
    private RestauranteSevice restauranteSevice;


    //@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }) // "produces" serve para :Esta metodo só produz o formato especifico de conteudo
    @GetMapping
    public List<Restaurante> listar() {
        return restauranteSevice.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscar(@PathVariable Long id) {
       return restauranteSevice.buscarOuFalhar(id);
    }

    @PostMapping
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        return restauranteSevice.salvar(restaurante);
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@RequestBody Restaurante restaurante,
                                 @PathVariable Long id) {
        Restaurante restauranteAtual = restauranteSevice.buscarOuFalhar(id);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id","formaPagamentos", "endereco", "dataCadastro","produtos");
        return restauranteSevice.salvar(restauranteAtual);
    }
    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id,
                                              @RequestBody Map<String, Object> campos){
        Restaurante restauranteAtual = restauranteSevice
                .buscarOuFalhar(id);
        merge(campos, restauranteAtual);
        return atualizar(restauranteAtual,id);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
            restauranteSevice.excluir(id);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {

        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field,restauranteDestino,novoValor);
        });

    }

}
