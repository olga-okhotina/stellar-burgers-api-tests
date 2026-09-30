package stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stellarburgers.order.OrderChecker;
import stellarburgers.order.OrderClient;
import stellarburgers.user.User;
import stellarburgers.user.UserChecker;
import stellarburgers.user.UserClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Получение заказов конкретного пользователя")
public class GetUserOrdersTest {

    private final UserClient userClient = new UserClient();
    private final UserChecker userCheck = new UserChecker();
    private final OrderClient orderClient = new OrderClient();
    private final OrderChecker orderCheck = new OrderChecker();

    private String accessToken;

    @BeforeEach
    public void setUp() {
        User user = User.random();
        ValidatableResponse response = userClient.create(user);
        accessToken = userCheck.createdSuccessfully(response);
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void getOrdersForAuthorizedUser() {
        ValidatableResponse response = orderClient.getUserOrdersWithAuth(accessToken);
        orderCheck.ordersReceivedSuccessfully(response);
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя возвращает ошибку")
    public void getOrdersForUnauthorizedUser() {
        ValidatableResponse response = orderClient.getUserOrdersWithoutAuth();
        String message = orderCheck.ordersFailed(response, 401);
        assertEquals("You should be authorised", message);
    }
}
