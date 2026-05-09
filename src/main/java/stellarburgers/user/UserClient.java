package stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarburgers.Client;

import java.util.Map;

public class UserClient extends Client {
    private static final String REGISTER = "/auth/register";
    private static final String LOGIN = "/auth/login";
    private static final String USER = "/auth/user";

    @Step("Создать пользователя")
    public ValidatableResponse create(User user) {
        return spec()
                .body(user)
                .when().post(REGISTER)
                .then().log().all();
    }

    @Step("Создать пользователя с произвольным телом запроса")
    public ValidatableResponse create(Map<String, String> body) {
        return spec()
                .body(body)
                .when().post(REGISTER)
                .then().log().all();
    }

    @Step("Логин пользователя")
    public ValidatableResponse login(User user) {
        return spec()
                .body(Map.of("email", user.getEmail(), "password", user.getPassword()))
                .when().post(LOGIN)
                .then().log().all();
    }

    @Step("Логин с произвольными данными")
    public ValidatableResponse login(String email, String password) {
        return spec()
                .body(Map.of("email", email, "password", password))
                .when().post(LOGIN)
                .then().log().all();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse delete(String token) {
        return specWithToken(token)
                .when().delete(USER)
                .then().log().all();
    }

    @Step("Изменить данные пользователя с авторизацией")
    public ValidatableResponse update(String token, Map<String, String> body) {
        return specWithToken(token)
                .body(body)
                .when().patch(USER)
                .then().log().all();
    }

    @Step("Изменить данные пользователя без авторизации")
    public ValidatableResponse updateWithoutAuth(Map<String, String> body) {
        return spec()
                .body(body)
                .when().patch(USER)
                .then().log().all();
    }
}
