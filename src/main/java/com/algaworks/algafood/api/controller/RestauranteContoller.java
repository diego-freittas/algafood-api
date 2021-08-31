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
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteSevice.findById(id);
        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }
        return ResponseEntity.notFound().build();
        //return ResponseEntity.status(HttpStatus.OK).body(cozinha);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteSevice.salvar(restaurante);
            //return ResponseEntity.created(URI).body(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@RequestBody Restaurante restaurante,
                                                 @PathVariable Long id) {
        Restaurante restauranteAtual = restauranteSevice.findById(id).orElse(null);
        if (restauranteAtual != null) {
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id","formaPagamentos", "endereco");
            restauranteSevice.salvar(restauranteAtual);
            return ResponseEntity.ok(restauranteAtual);
        }
        return ResponseEntity.notFound().build();
    }
        @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id,
                                              @RequestBody Map<String, Object> campos){
        Restaurante restauranteAtual = restauranteSevice
                .findById(id).orElse(null);
        if (restauranteAtual == null){
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual);
        return atualizar(restauranteAtual,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurante> remover(@PathVariable Long id) {
        try {
            restauranteSevice.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
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
