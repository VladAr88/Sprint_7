package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.example.Constants.getOrders;

public class GetOrdersTest extends TestBase{

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.baseURI;
    }

    @Test
    public void getOrdersListTest() throws Exception {
        createSeveralOrders(2);

        Orders orders =
            given()
                .header("Content-type", "application/json")
                .get(baseURI + getOrders)
                .body().as(Orders.class);
        Assert.assertNotNull(orders.getOrder());
    }
}
