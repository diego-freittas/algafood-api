package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.imput.RestauranteDTOImput;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteContoller {

    @Autowired
    private RestauranteSevice restauranteSevice;

    @Autowired
    private SmartValidator validator;


    //@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }) // "produces" serve para :Esta metodo só produz o formato especifico de conteudo
    @GetMapping
    public List<RestauranteDTO> listar() {

        return toCollectionDTO(restauranteSevice.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteSevice.buscarOuFalhar(id);
        return toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOImput restauranteDTOImput) {
        try {
            Restaurante restaurante = toDomainObject(restauranteDTOImput);
            return toDTO(restauranteSevice.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                 @RequestBody  @Valid RestauranteDTOImput restauranteDTOImput) {
        try {
            Restaurante restaurante = toDomainObject(restauranteDTOImput);

            Restaurante restauranteAtual = restauranteSevice.buscarOuFalhar(restauranteId);
            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return toDTO(restauranteSevice.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

//    @PatchMapping("/{id}")
//    public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId,
//                                        @RequestBody Map<String, Object> campos, HttpServletRequest request) {
//        Restaurante restauranteDoBancoDeDados = restauranteSevice.buscarOuFalhar(restauranteId);
//        merge(campos, restauranteDoBancoDeDados, request);
//        validate(restauranteDoBancoDeDados, "restaurante");
//        RestauranteDTOImput restauranteDTOImput = new RestauranteDTOImput();
//        restauranteDTOImput.setNome();
//
//        return atualizar(restauranteId, restauranteDTO);
//
//    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {

        restauranteSevice.excluir(id);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                       HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    private RestauranteDTO toDTO(Restaurante restaurante) {
        RestauranteDTO restauranteDTO = new RestauranteDTO();
        CozinhaDTO cozinhaDTO = new CozinhaDTO();

        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());

        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        restauranteDTO.setCozinha(cozinhaDTO);
        return restauranteDTO;
    }
    private List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(restaurante -> toDTO(restaurante))
                .collect(Collectors.toList());
    }
    private Restaurante toDomainObject(RestauranteDTOImput restauranteDTOImput){

        Restaurante restaurante = new Restaurante();
        Cozinha cozinha = new Cozinha();
        restaurante.setNome(restauranteDTOImput.getNome());
        restaurante.setTaxaFrete(restauranteDTOImput.getTaxaFrete());
        cozinha.setId(restauranteDTOImput.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restaurante;
    }

}
