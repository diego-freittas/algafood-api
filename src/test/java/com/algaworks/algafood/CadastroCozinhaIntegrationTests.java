package com.algaworks.algafood;


import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {

    @Autowired
    private CozinhaService cozinhaService;

    @Test
    public void testarCadastroCozinhaComSucesso(){
        //Teste do caminho feliz

        //Cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa do teste");
        //Ação
        novaCozinha = cozinhaService.salvar(novaCozinha);
        //Validação
        org.assertj.core.api.Assertions.assertThat(novaCozinha).isNotNull();
        org.assertj.core.api.Assertions.assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    public void deveFalharAoCadastrarCozinha_QuandoSemNome(){
        //Cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);
        //Ação
        //Validação
        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cozinhaService.salvar(novaCozinha);
                });
    }

}
