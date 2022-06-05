package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserPostModel;
import org.junit.Assert;

import static io.restassured.RestAssured.given;


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

    @Step("Send patch request and check the field")
    public void sendPatchUserRequestAndCheckTheResponseBody(UserPostModel userPost, String accessToken, Boolean isAuthorized, String path, String expectedValue) {
        if(isAuthorized) {
            Response responsePatch = sendPatchUserRequest(userPost, accessToken);
            checkStatusCode(responsePatch, 200);
            Response responseGet = sendGetUserRequest(accessToken);
            checkStatusCode(responseGet, 200);
            Assert.assertEquals(responseGet.path("user." + path), expectedValue);
        } else {
            Response responsePatch = sendPatchUserRequest(userPost);
            checkStatusCode(responsePatch, 401);
            Assert.assertEquals(responsePatch.path("success"), false);
            Assert.assertEquals(responsePatch.path("message"), "You should be authorised");
        }
    }
}

