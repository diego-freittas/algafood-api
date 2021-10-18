package com.algaworks.algafood.util;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteSevice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RestauranteServiceTeste {

    @Autowired
    private RestauranteSevice restauranteSevice;

    @Test
    public void deveRetornarTrue_QuandoRestaurateForAtivado(){
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setAtivo(false);
        restaurante.ativar();
        boolean resultadoEsperado = true;
        Assertions.assertEquals(resultadoEsperado,restaurante.isAtivo());
    }
}
