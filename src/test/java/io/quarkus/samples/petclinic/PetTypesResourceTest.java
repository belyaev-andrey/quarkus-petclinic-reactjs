package io.quarkus.samples.petclinic;

import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PetTypesResourceTest {

    @Test
    public void testListAllPetTypes() {
        given()
                .when()
                .auth().preemptive().basic("admin", "admin")
                .get("api/pettypes")
                .then()
                .statusCode(200)
                .body(
                        containsString("cat"),
                        containsString("dog"),
                        containsString("lizard"),
                        containsString("snake"),
                        containsString("bird"),
                        containsString("hamster")
                );
    }


    @Test
    public void testFindPetTypeById() {
        given()
                .when()
                .auth().preemptive().basic("admin", "admin")
                .get("api/pettypes/2")
                .then()
                .statusCode(200)
                .body(
                        containsString("dog")
                );
    }

    @Test
    public void testFindPetTypeByIdNotFound() {
        given()
                .when()
                .auth().preemptive().basic("admin", "admin")
                .get("api/pettypes/0")
                .then()
                .statusCode(404);
    }

    @Test
    public void testFindPetTypeByIdNoAccess() {
        given()
                .when()
                .auth().preemptive().basic("anonymous", "anonymous")
                .get("api/pettypes/1")
                .then()
                .statusCode(401);
    }


    @Test
    public void testAddNewType() {
        given()
                .when()
                .auth().preemptive().basic("admin", "admin")
                .body(new PetType(7, "crocodile"), ObjectMapperType.GSON)
                .contentType("application/json;charset=utf-8")
                .post("/api/pettypes")
                .then()
                .statusCode(201)
                .header("Location", containsString("/api/pettypes/7"));
    }


    @Test
    public void testUpdateType() {
        given()
                .when()
                .auth().preemptive().basic("admin", "admin")
                .body(new PetType(1, "The Cat"), ObjectMapperType.GSON)
                .contentType("application/json;charset=utf-8")
                .put("/api/pettypes/1")
                .then()
                .statusCode(204);

        given()
                .when()
                .auth().preemptive().basic("admin", "admin")
                .get("api/pettypes/1")
                .then()
                .statusCode(200)
                .body(
                        containsString("The Cat")
                );

    }


}