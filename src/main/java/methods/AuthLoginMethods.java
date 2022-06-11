package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserPostModel;

public class AuthLoginMethods extends BaseMethods {

    private final String url = "/api/auth/login";

    @Step("Send post /api/auth/login request")
    public Response sendPostLoginRequest(UserPostModel userPost) {
        Response response = sendPostRequest(userPost, url);
        return response;
    }
}
