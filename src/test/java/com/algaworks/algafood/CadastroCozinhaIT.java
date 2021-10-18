package com.algaworks.algafood;


import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

  @LocalServerPort
  private int port;

  private static final int COZINHA_ID_INEXISTENTE = 100;
  private Cozinha cozinhaAmericana;
  private int quantidadeDeCozinhas;
  private String jsonCorretoCozinhaChinesa;

  @Autowired
  private DatabaseCleaner databaseCleaner;

  @Autowired
  private CozinhaService cozinhaService;

  @BeforeEach
  public void setUp(){
    //Esse método é chamado antes de cada teste.
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = port;
    RestAssured.basePath = "/cozinhas";

    databaseCleaner.clearTables();
    this.prepararDados();
    jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
            "/json/correto/cozinha_chinesa.json");

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
  public void deveRetornarAQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas(){
    //habilita o log no console
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    // Dado que o basePath("/cozinhas") e a port a8080 e o retorno for um Json
    RestAssured.given()
              .accept(ContentType.JSON)
            .when() // quando fizer uma requisição GET
              .get()
            .then()//Então o retorno deve ser Status Code 200.
              .body("", Matchers.hasSize(quantidadeDeCozinhas));
            //.body("nome", Matchers.hasItems("Indiana", "Tailandesa"));
  }

  @Test
  public void deveRetornarStatus201_QuandoCadastrarCozinha(){
    RestAssured.given()
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .when()
            .post()
            .then()
            .statusCode(HttpStatus.CREATED.value());
  }

  @Test
  public void deveRetornarRespostaEstatusCorretos_QuandoConsultarCozinhaExistente(){
    RestAssured.given()
            .pathParam("cozinhaId",cozinhaAmericana.getId())
            .accept(ContentType.JSON)
            .when()
            .get("/{cozinhaId}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));
  }
  @Test
  public void deveRetornarRespostaEstatus404_QuandoConsultarCozinhaInexistente(){
    RestAssured.given()
            .pathParam("cozinhaId",COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
            .when()
            .get("/{cozinhaId}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
  }

  private void prepararDados(){

    Cozinha cozinha1 = new Cozinha();
    cozinha1.setNome("Tailandesa");
    cozinhaService.salvar(cozinha1);

    cozinhaAmericana = new Cozinha();
    cozinhaAmericana.setNome("Americana");
    cozinhaService.salvar(cozinhaAmericana);

    //Conta a quantidade de registros no banco de dados e armazenará o valor na variável quantidadeDeCozinhas
    quantidadeDeCozinhas = (int) cozinhaService.count();
  }

}
