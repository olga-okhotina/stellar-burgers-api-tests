package stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.order.Order;
import stellarburgers.order.OrderChecker;
import stellarburgers.order.OrderClient;
import stellarburgers.user.User;
import stellarburgers.user.UserChecker;
import stellarburgers.user.UserClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Создание заказа")
public class CreateOrderTest {

    private final UserClient userClient = new UserClient();
    private final UserChecker userCheck = new UserChecker();
    private final OrderClient orderClient = new OrderClient();
    private final OrderChecker orderCheck = new OrderChecker();

    private String accessToken;
    private String validIngredientHash;

    @BeforeEach
    public void setUp() {
        User user = User.random();
        ValidatableResponse userResponse = userClient.create(user);
        accessToken = userCheck.createdSuccessfully(userResponse);

        ValidatableResponse ingredientsResponse = orderClient.getIngredients();
        validIngredientHash = ingredientsResponse.extract().path("data[0]._id");
        assertNotNull(validIngredientHash, "Не удалось получить хеш ингредиента");
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuth() {
        Order order = new Order(List.of(validIngredientHash));
        ValidatableResponse response = orderClient.createWithAuth(accessToken, order);
        orderCheck.createdSuccessfully(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        Order order = new Order(List.of(validIngredientHash));
        ValidatableResponse response = orderClient.createWithoutAuth(order);
        orderCheck.createdSuccessfully(response);
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void createOrderWithIngredients() {
        Order order = new Order(List.of(validIngredientHash));
        ValidatableResponse response = orderClient.createWithAuth(accessToken, order);
        orderCheck.createdSuccessfully(response);
        String orderName = response.extract().path("name");
        assertNotNull(orderName);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        Order order = new Order(new ArrayList<>());
        ValidatableResponse response = orderClient.createWithAuth(accessToken, order);
        String message = orderCheck.creationFailed(response, 400);
        assertNotNull(message);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithWrongIngredientHash() {
        Order order = new Order(List.of("invalid_hash_000000000000000000000000"));
        ValidatableResponse response = orderClient.createWithAuth(accessToken, order);
        orderCheck.failedWithStatusCode(response, 500);
    }
}
