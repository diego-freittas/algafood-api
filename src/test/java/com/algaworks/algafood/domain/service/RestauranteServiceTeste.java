package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.AplicationConfigTeste;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@DisplayName("RestauranteServiceTeste")
public class RestauranteServiceTeste extends AplicationConfigTeste {

    @MockBean
    private RestauranteRepository restauranteRepository;

    @MockBean
    private CozinhaService cozinhaService;

    @MockBean
    private CidadeService cidadeService;

    @MockBean
    private FormaPagamentoService formaPagamentoService;

    @MockBean
    private ProdutoService produtoService;

    @Autowired // A unica classe realmente injetada é a classe que vamos testar, as dependencias dela seram injetadas
    //via @MockBean
    private RestauranteSevice restauranteSevice;

    @Test
    @DisplayName("deve salvar um restaurante")
    public void deveRetornarUmRestauante_QuandoUmRestauranteForSalvo() {

        Restaurante restaurante1 = criarRestaurante();
        restaurante1.getNome();
        restauranteSevice.salvar(restaurante1);
        Mockito.verify(restauranteRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Restaurante.class));
        //Verifique quantas vezes o restauranteRepository chamou o metodo salvar passando como argumento
        // um restaurante.

    }

    @Test
    @DisplayName("deve remover um restaurante")
    public void deveRemoverUMRestauante_QuandoOMetodoExluirForChamado() {
        Long idRestaurante = 1L;
        restauranteSevice.excluir(idRestaurante);

        //Verifique quantas vezes o restauranteRepository chamou o deleteById salvar passando como argumento
        // Long.
        Mockito.verify(restauranteRepository, Mockito.times(1))
                .deleteById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void deveRetornarTrue_QuandoRestaurateForAtivado() {

        Restaurante restaurante = Mockito.mock(Restaurante.class);
        Mockito.when(restaurante.getId()).thenReturn(1L);
        Long idRestaurante = restaurante.getId();
        Mockito.when(restauranteSevice.buscarOuFalhar(ArgumentMatchers.eq(idRestaurante)))
                .thenReturn(restaurante);
       // restauranteSevice.ativar(restaurante.getId());

    }

    private Restaurante criarRestaurante() {
        Restaurante restaurante = Mockito.mock(Restaurante.class);
        Mockito.when(restaurante.getId()).thenReturn(1L);
        Mockito.when(restaurante.getNome()).thenReturn("Restaurnate Mockito");

        return restaurante;
    }
}
