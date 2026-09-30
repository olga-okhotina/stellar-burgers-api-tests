package stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarburgers.Client;

public class OrderClient extends Client {
    private static final String ORDERS = "/orders";
    private static final String INGREDIENTS = "/ingredients";

    @Step("Получить список ингредиентов")
    public ValidatableResponse getIngredients() {
        return spec()
                .when().get(INGREDIENTS)
                .then().log().all();
    }

    @Step("Создать заказ с авторизацией")
    public ValidatableResponse createWithAuth(String token, Order order) {
        return specWithToken(token)
                .body(order)
                .when().post(ORDERS)
                .then().log().all();
    }

    @Step("Создать заказ без авторизации")
    public ValidatableResponse createWithoutAuth(Order order) {
        return spec()
                .body(order)
                .when().post(ORDERS)
                .then().log().all();
    }

    @Step("Получить заказы пользователя с авторизацией")
    public ValidatableResponse getUserOrdersWithAuth(String token) {
        return specWithToken(token)
                .when().get(ORDERS)
                .then().log().all();
    }

    @Step("Получить заказы пользователя без авторизации")
    public ValidatableResponse getUserOrdersWithoutAuth() {
        return spec()
                .when().get(ORDERS)
                .then().log().all();
    }
}
