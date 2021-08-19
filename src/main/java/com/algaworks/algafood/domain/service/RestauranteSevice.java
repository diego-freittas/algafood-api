package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteSevice {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Restaurante salvar(Restaurante restaurante) {

        return restauranteRepository.salvar(restaurante);
    }

    public void excluir(Long id) {
        try {
            restauranteRepository.remover(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado uma cozinha com o código %d: ", id));
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));

        }
    }

    public Restaurante buscar(Long id) {
        return restauranteRepository.buscar(id);
    }

    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }
}
