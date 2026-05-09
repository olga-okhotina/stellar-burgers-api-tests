package stellarburgers;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Client {

    public RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URI)
                .basePath(EnvConfig.BASE_PATH);
    }

    public RequestSpecification specWithToken(String token) {
        return spec().header("Authorization", token);
    }
}
