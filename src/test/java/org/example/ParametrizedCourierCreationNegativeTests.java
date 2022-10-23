package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class ParametrizedCourierCreationNegativeTests {

    private String createCourierUrl = "/api/v1/courier";
    private final String login;
    private final String password;
    private final String firstName;

    public ParametrizedCourierCreationNegativeTests(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] courierInsufficientData() {
        return new Object[][]{
            {RandomStringUtils.random(10), RandomStringUtils.random(10), null},
            {RandomStringUtils.random(10), null, RandomStringUtils.random(10)},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void incorrectCourierCreationTest() throws Exception {
        Courier newCourier = new Courier();
        newCourier.setFirstName(login);
        newCourier.setLogin(password);
        newCourier.setPassword(firstName);
        String message = "Недостаточно данных для создания учетной записи";
        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post(baseURI + createCourierUrl);
        response.then().assertThat().body("message", equalTo(message))
            .and()
            .statusCode(400);
    }
}
