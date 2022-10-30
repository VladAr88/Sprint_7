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
public class ParametrizedCourierLoginNegativeTests extends TestBase {

    private final String login;
    private final String password;

    public ParametrizedCourierLoginNegativeTests(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] courierLoginInsufficientData() {
        return new Object[][]{
            {RandomStringUtils.random(10), null},
            {null, RandomStringUtils.random(10)},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void incorrectCourierLoginTest() throws Exception {
        Courier courierToLogin = createDefaultCourier();
        courierToLogin.setLogin(login);
        courierToLogin.setPassword(password);

        String message = "Недостаточно данных для входа";
        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(courierToLogin)
                .when()
                .post(baseURI + Constants.loginUrl);
        response.then().assertThat().body("message", equalTo(message))
            .and()
            .statusCode(400);
    }
}
