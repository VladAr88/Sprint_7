package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends TestBase {


    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.baseURI;
    }

    @Test
    public void checkCourierPositiveLoginTest() {
        Courier courierToLogin = createDefaultCourier();

        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(courierToLogin)
                .when()
                .post(baseURI + Constants.loginUrl);
        response.then().assertThat().body("id", notNullValue())
            .and()
            .statusCode(200);
    }

    @Test
    public void checkNonExistedCourierLoginTest() {
        Courier courierToLogin = new Courier();
        courierToLogin.setLogin(RandomStringUtils.random(10));
        courierToLogin.setPassword(RandomStringUtils.random(10));

        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(courierToLogin)
                .when()
                .post(baseURI + Constants.loginUrl);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
            .and()
            .statusCode(404);
    }

    @Test
    public void checkUnmatchedCredentialsCourierLoginTest() {
        Courier courierToLogin = createDefaultCourier();
        courierToLogin.setPassword(RandomStringUtils.random(10));

        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(courierToLogin)
                .when()
                .post(baseURI + Constants.loginUrl);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
            .and()
            .statusCode(404);
    }
}
