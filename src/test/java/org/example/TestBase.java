package org.example;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestBase {

    public Courier createDefaultCourier() {
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
        response.then().assertThat().statusCode(201);

        return newCourier;
    }

    public Order createDefaultOrder() {
        Order defaultOrder = new Order();
        defaultOrder.setFirstName("Naruto");
        defaultOrder.setLastName("Uchiha");
        defaultOrder.setAddress("Konoha, 142 apt.");
        defaultOrder.setMetroStation("4");
        defaultOrder.setPhone("+7 800 355 35 35");
        defaultOrder.setRentTime("5");
        defaultOrder.setDeliveryDate("2020-06-06");
        defaultOrder.setComment("Saske, come back to Konoha");
        return defaultOrder;
    }

    public void createSeveralOrders(int amountOfOrders) {
        for (int i = 0; i < amountOfOrders; i++) {
            createDefaultOrder();
        }
    }

    public void deleteCourier(Courier courierToDelete) {

        Response response =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(courierToDelete)
                .when()
                .post(baseURI + Constants.loginUrl);
        String loginResult = response.getBody().asString();
        String id = returnCourierId(loginResult);

        CourierLoginResponse courierLoginResponse = new CourierLoginResponse();
        courierLoginResponse.setId(id);

        Response responseToDelete =
            given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLoginResponse)
                .when()
                .delete(baseURI + Constants.deleteCourier + courierLoginResponse.getId());
        responseToDelete.then().assertThat().statusCode(200);
    }

    public String returnCourierId(String rawCourierId) {
        String id = rawCourierId.substring(6);
        return id.replaceAll("}", "");
    }
}
