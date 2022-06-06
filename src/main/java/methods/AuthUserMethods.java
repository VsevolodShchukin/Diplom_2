package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserPostModel;

public class AuthUserMethods extends BaseMethods {

    private final String url = "/api/auth/user";

    @Step("Send patch /api/auth/user request with token")
    public Response sendPatchUserRequest(UserPostModel userPost, String token) {
        Response response = sendPatchRequest(userPost, url, token);
        return response;
    }

    @Step("Send patch /api/auth/user request")
    public Response sendPatchUserRequest(UserPostModel userPost) {
        Response response = sendPatchRequest(userPost, url);
        return response;
    }

    @Step("Send get /api/auth/user request")
    public Response sendGetUserRequest(String token) {
        Response response = sendGetRequest(url, token);
        return response;
    }

    @Step("Send delete /api/auth/user request")
    public Response sendDeleteUserRequest(String token) {
        if(token == null) {
            return null;
        }
        Response response = sendDeleteRequest(url, token);
        return response;
    }

}

