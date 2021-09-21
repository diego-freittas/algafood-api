package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
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

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaRepository cozinhaRepository;

    Restaurante restauranteBahiano;

    private int quantidadeDeRestaurantesCadastrados;

    private String jsonCorretoRestauranteBahiano;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
    private static final int RESTAURANTE_ID_INEXISTENTE = 100;
    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";




    @BeforeEach
    public void setUp(){
        //Esse método é chamado antes de cada teste.
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        this.prepararDados();
        jsonCorretoRestauranteBahiano = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante_baiano.json");
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");

        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");

        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

    }

    //Criar um teste para consulta de restaurante - Caminho Feliz
    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurante(){
        // Dado que o basePath("/cozinhas") e a port a8080 e o retorno for um Json
        RestAssured.given()
                .accept(ContentType.JSON)
                .when() // quando fizer uma requisição GET
                .get()
                .then()//Então o retorno deve ser Status Code 200.
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarAQuantidadeCorretaDeRestaurante_QuandoConsultarRestaurante(){

        quantidadeDeRestaurantesCadastrados = (int) restauranteRepository.count();
        RestAssured.given()
                    .accept(ContentType.JSON)
                .when() // Quando
                    .get()
                .then()//Então
                    .body("", Matchers.hasSize(quantidadeDeRestaurantesCadastrados));
    }
    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        RestAssured.given()
                .pathParam("restauranteId", restauranteBahiano.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo(restauranteBahiano.getNome()));
    }


    //Criar um teste para consulta de restaurante - Caminho Infeliz

    //Criar um teste para cadastro de restaurante - Caminho Feliz
    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        RestAssured.given()
                .body(jsonCorretoRestauranteBahiano)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when() // Quando
                .post()
                .then()//Então
                .statusCode(HttpStatus.CREATED.value());
    }
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        RestAssured.given()
                .body(jsonRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value()) // Verificar pq a exeption ta retornando erro 500 ao invés de 400
                .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        RestAssured.given()
                .body(jsonRestauranteSemCozinha)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        RestAssured.given()
                .body(jsonRestauranteComCozinhaInexistente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        RestAssured.given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{restauranteId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


        //Criar um teste para cadastro de restaurante - Caminho Infeliz

    private void prepararDados(){

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        BigDecimal taxaFrete = new BigDecimal(10);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Restaurante 1 do teste");
        restaurante1.setTaxaFrete(taxaFrete);
        restaurante1.setCozinha(cozinha1);
        restauranteRepository.save(restaurante1);

        restauranteBahiano = new Restaurante();
        restauranteBahiano.setNome("Comida Bahiana");
        restauranteBahiano.setTaxaFrete(taxaFrete);
        restauranteBahiano.setCozinha(cozinha1);
        restauranteRepository.save(restauranteBahiano);

        //Conta a quantidade de registros no banco de dados e armazenará o valor na variável quantidadeDeCozinhas
        quantidadeDeRestaurantesCadastrados = (int) restauranteRepository.count();
    }


}
