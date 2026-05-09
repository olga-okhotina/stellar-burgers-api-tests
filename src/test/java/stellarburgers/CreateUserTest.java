package stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.user.User;
import stellarburgers.user.UserChecker;
import stellarburgers.user.UserClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Создание пользователя")
public class CreateUserTest {

    private final UserClient client = new UserClient();
    private final UserChecker check = new UserChecker();

    private String accessToken;

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            client.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUniqueUser() {
        User user = User.random();
        ValidatableResponse response = client.create(user);
        accessToken = check.createdSuccessfully(response);
    }

    @Test
    @DisplayName("Нельзя создать пользователя, который уже зарегистрирован")
    public void createAlreadyRegisteredUser() {
        User user = User.random();
        ValidatableResponse firstResponse = client.create(user);
        accessToken = check.createdSuccessfully(firstResponse);

        ValidatableResponse duplicateResponse = client.create(user);
        String message = check.creationFailed(duplicateResponse, 403);
        assertEquals("User already exists", message);
    }

    @Test
    @DisplayName("Создание пользователя без поля email")
    public void createUserWithoutEmail() {
        Map<String, String> body = new HashMap<>();
        body.put("password", "Pass123!");
        body.put("name", "Test User");

        ValidatableResponse response = client.create(body);
        String message = check.creationFailed(response, 403);
        assertNotNull(message);
    }

    @Test
    @DisplayName("Создание пользователя без поля password")
    public void createUserWithoutPassword() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "test_nopwd@test.com");
        body.put("name", "Test User");

        ValidatableResponse response = client.create(body);
        String message = check.creationFailed(response, 403);
        assertNotNull(message);
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    public void createUserWithoutName() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "test_noname@test.com");
        body.put("password", "Pass123!");

        ValidatableResponse response = client.create(body);
        String message = check.creationFailed(response, 403);
        assertNotNull(message);
    }
}
