package com.algaworks.algafood;


import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {

  @LocalServerPort
  private int port;

  @Test
  public void deveRetornarStatus200_QuandoConsultarCozinhas(){
    //habilita o log no console
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    // Dado que o basePath("/cozinhas") e a port a8080 e o retorno for um Json
    RestAssured.given()
                  .basePath("/cozinhas")
                  .port(port)
                  .accept(ContentType.JSON)
                .when() // quando fizer uma requisição GET
                  .get()
                .then()//Então o retorno deve ser Status Code 200.
                  .statusCode(HttpStatus.OK.value());
  }


}
