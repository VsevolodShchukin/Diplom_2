package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.UserPostModel;

public class AuthRegisterMethods extends BaseMethods {

    private final String url = "/api/auth/register";

    @Step("Send post /api/auth/register request")
    public Response sendPostAuthRegisterRequest(UserPostModel userPost) {
        Response response = sendPostRequest(userPost, url);
        return response;
    }


}
