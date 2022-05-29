package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserPostModel;


public class AuthUserMethods extends BaseMethods {

    private final String url = "/api/auth/user";

    @Step("Send patch /api/auth/user request")
    public Response sendPatchUserRequest(UserPostModel userPost, String token) {
        Response response = sendPatchRequest(userPost, url, token);
        return response;
    }

    @Step("Send get /api/auth/user request")
    public Response sendGetUserRequest(String token) {
        Response response = sendGetRequest(token);
        return response;
    }
}

