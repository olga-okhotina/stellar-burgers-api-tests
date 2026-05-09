package stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserChecker {

    @Step("Пользователь успешно создан")
    public String createdSuccessfully(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract().path("success");
        assertTrue(success);
        String token = response.extract().path("accessToken");
        assertNotNull(token);
        return token;
    }

    @Step("Создание пользователя завершилось ошибкой")
    public String creationFailed(ValidatableResponse response, int statusCode) {
        return response
                .assertThat()
                .statusCode(statusCode)
                .extract().path("message");
    }

    @Step("Логин прошёл успешно")
    public String loggedInSuccessfully(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract().path("success");
        assertTrue(success);
        String token = response.extract().path("accessToken");
        assertNotNull(token);
        return token;
    }

    @Step("Логин завершился ошибкой")
    public String loginFailed(ValidatableResponse response, int statusCode) {
        return response
                .assertThat()
                .statusCode(statusCode)
                .extract().path("message");
    }

    @Step("Данные пользователя успешно обновлены")
    public void updatedSuccessfully(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract().path("success");
        assertTrue(success);
    }

    @Step("Обновление данных завершилось ошибкой")
    public String updateFailed(ValidatableResponse response, int statusCode) {
        return response
                .assertThat()
                .statusCode(statusCode)
                .extract().path("message");
    }
}
