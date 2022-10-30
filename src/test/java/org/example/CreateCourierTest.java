package org.example;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class CreateCourierTest extends TestBase{

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.baseURI;
    }

    //курьера можно создать
    //запрос возвращает правильный код ответа
    //успешный запрос возвращает ok: true
    @Test
    public void courierCreationPositiveTest() {
        Courier newCourier = new Courier();
        newCourier.setFirstName(RandomStringUtils.random(10));
        newCourier.setLogin(RandomStringUtils.random(10));
        newCourier.setPassword(RandomStringUtils.random(10));

        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post(baseURI + Constants.createCourierUrl);
        response.then().assertThat().body("ok", equalTo(true))
            .and()
            .statusCode(201);

        deleteCourier(newCourier);
    }

    @Test
    public void sameLoginCourierCreationTest() {
        String loginDuplicated = RandomStringUtils.random(10);

        Courier credentials = new Courier();
        credentials.setLogin(loginDuplicated);
        String password = "1234";
        credentials.setPassword(password);

        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(credentials)
                .when()
                .post(baseURI + Constants.createCourierUrl);
        response.then().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));

        Response secondResponse =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(credentials)
                .when()
                .post(baseURI + Constants.createCourierUrl);
        secondResponse.then().statusCode(409);
        secondResponse.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        deleteCourier(credentials);
    }


}
