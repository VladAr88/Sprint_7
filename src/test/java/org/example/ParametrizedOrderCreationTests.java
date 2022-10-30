package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.Console;
import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.example.Constants.createOrder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class ParametrizedOrderCreationTests extends TestBase {

    private static String[] color;

    public ParametrizedOrderCreationTests(String[] color) {
        ParametrizedOrderCreationTests.color = color;
    }

    @Parameterized.Parameters
    public static Collection orderColorData() {
        return Arrays.asList(new String[][][]{
            {{"BLACK", "GREY"}},
            {{"BLACK"}},
            {{"GREY"}},
            {{}},

        });
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.baseURI;
    }

    @Test
    public void incorrectCourierCreationTest() throws Exception {
        Order orderToPost = createDefaultOrder();
        orderToPost.setColor(color);

        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(orderToPost)
                .when()
                .post(baseURI + createOrder);
        response.then().assertThat().body("track", notNullValue())
            .and()
            .statusCode(201);
    }
}