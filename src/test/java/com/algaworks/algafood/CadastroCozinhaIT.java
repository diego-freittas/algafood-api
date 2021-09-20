package com.algaworks.algafood;


import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

  @Autowired
  private Flyway flyway;

  @BeforeEach
  public void setUp(){
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = port;
    RestAssured.basePath = "/cozinhas";

    flyway.migrate(); // Restaura o banco para um estado inicial
  }

  @Test
  public void deveRetornarStatus200_QuandoConsultarCozinhas(){


    // Dado que o basePath("/cozinhas") e a port a8080 e o retorno for um Json
    RestAssured.given()
                  .accept(ContentType.JSON)
                .when() // quando fizer uma requisição GET
                  .get()
                .then()//Então o retorno deve ser Status Code 200.
                  .statusCode(HttpStatus.OK.value());
  }

  @Test
  public void deveConterQuatroCozinhas_QuandoConsultarCozinhas(){
    //habilita o log no console
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    // Dado que o basePath("/cozinhas") e a port a8080 e o retorno for um Json
    RestAssured.given()
              .accept(ContentType.JSON)
            .when() // quando fizer uma requisição GET
              .get()
            .then()//Então o retorno deve ser Status Code 200.
              .body("", Matchers.hasSize(4));
            //.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
  }

  @Test
  public void deveRetornarStatus201_QuandoCadastrarCozinha(){
    RestAssured.given()
            .body("{ \"nome\": \"Chinesa\" }")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .when()
            .post()
            .then()
            .statusCode(HttpStatus.CREATED.value());
  }




}
