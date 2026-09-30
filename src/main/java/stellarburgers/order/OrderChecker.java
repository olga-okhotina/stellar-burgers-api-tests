package stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderChecker {

    @Step("Заказ успешно создан")
    public void createdSuccessfully(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract().path("success");
        assertTrue(success);
    }

    @Step("Создание заказа завершилось ошибкой")
    public String creationFailed(ValidatableResponse response, int statusCode) {
        return response
                .assertThat()
                .statusCode(statusCode)
                .extract().path("message");
    }

    @Step("Сервер вернул ошибку с кодом {statusCode}")
    public void failedWithStatusCode(ValidatableResponse response, int statusCode) {
        response.assertThat().statusCode(statusCode);
    }

    @Step("Список заказов получен успешно")
    public void ordersReceivedSuccessfully(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract().path("success");
        assertTrue(success);
    }

    @Step("Получение заказов завершилось ошибкой")
    public String ordersFailed(ValidatableResponse response, int statusCode) {
        return response
                .assertThat()
                .statusCode(statusCode)
                .extract().path("message");
    }
}
