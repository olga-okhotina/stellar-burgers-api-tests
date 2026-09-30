package stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.user.User;
import stellarburgers.user.UserChecker;
import stellarburgers.user.UserClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Изменение данных пользователя")
public class UpdateUserTest {

    private final UserClient client = new UserClient();
    private final UserChecker check = new UserChecker();

    private String accessToken;

    @BeforeEach
    public void setUp() {
        User user = User.random();
        ValidatableResponse response = client.create(user);
        accessToken = check.createdSuccessfully(response);
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            client.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Изменение email с авторизацией")
    public void updateEmailWithAuth() {
        Map<String, String> body = Map.of("email", "updated_email@test.com");
        ValidatableResponse response = client.update(accessToken, body);
        check.updatedSuccessfully(response);
    }

    @Test
    @DisplayName("Изменение name с авторизацией")
    public void updateNameWithAuth() {
        Map<String, String> body = Map.of("name", "Updated Name");
        ValidatableResponse response = client.update(accessToken, body);
        check.updatedSuccessfully(response);
    }

    @Test
    @DisplayName("Изменение password с авторизацией")
    public void updatePasswordWithAuth() {
        Map<String, String> body = Map.of("password", "NewPassword123!");
        ValidatableResponse response = client.update(accessToken, body);
        check.updatedSuccessfully(response);
    }

    @Test
    @DisplayName("Изменение email без авторизации возвращает ошибку")
    public void updateEmailWithoutAuth() {
        Map<String, String> body = Map.of("email", "updated_unauth@test.com");
        ValidatableResponse response = client.updateWithoutAuth(body);
        String message = check.updateFailed(response, 401);
        assertEquals("You should be authorised", message);
    }

    @Test
    @DisplayName("Изменение name без авторизации возвращает ошибку")
    public void updateNameWithoutAuth() {
        Map<String, String> body = Map.of("name", "Unauthorized Name");
        ValidatableResponse response = client.updateWithoutAuth(body);
        String message = check.updateFailed(response, 401);
        assertEquals("You should be authorised", message);
    }
}
