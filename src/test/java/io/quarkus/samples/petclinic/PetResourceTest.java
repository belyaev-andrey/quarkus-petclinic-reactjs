package io.quarkus.samples.petclinic;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PetResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/pettypes")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}