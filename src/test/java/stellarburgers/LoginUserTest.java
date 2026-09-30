package stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.user.User;
import stellarburgers.user.UserChecker;
import stellarburgers.user.UserClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Логин пользователя")
public class LoginUserTest {

    private final UserClient client = new UserClient();
    private final UserChecker check = new UserChecker();

    private User user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        user = User.random();
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
    @DisplayName("Логин под существующим пользователем")
    public void loginExistingUser() {
        ValidatableResponse response = client.login(user);
        String token = check.loggedInSuccessfully(response);
        accessToken = token;
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void loginWithWrongCredentials() {
        ValidatableResponse response = client.login("wrong_user@test.com", "wrongpassword");
        String message = check.loginFailed(response, 401);
        assertEquals("email or password are incorrect", message);
    }
}
