package com.example.demo.resources;

import com.example.demo.DemoApplicationTests;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 09/09/17.
 */
public class PessoaResourceTest extends DemoApplicationTests {

    @Test
    public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
        given()
                .pathParam("ddd", "86")
                .pathParam("numero", "35006330")
        .get("/pessoas/{ddd}/{numero}")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("codigo", equalTo(3),
                        "nome", equalTo("Cauê"),
                        "cpf", equalTo("38767897100"));
    }

    @Test
    public void deve_retornar_erro_nao_encontrado_quando_buscar_pessoa_por_telefone_inexistente() throws Exception {
        given()
                .pathParam("ddd", "99")
                .pathParam("numero", "987654321")
        .get("/pessoas/{ddd}/{numero}")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("erro", equalTo("Não existe pessoa com o telefone (99)987654321"));
    }
}
