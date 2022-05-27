package methods;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.PostBaseModel;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class BaseMethods {

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    @Step("Check status code")
    public void checkStatusCode(Response response, int code) {
        int responseCode = response.statusCode();
        Assert.assertEquals(code, responseCode);
    }

    @Step("Check body from response")
    public void checkBodyFromResponse(Response response, String expectedBody) {
        String responseBody = response.body().asString();
        Assert.assertEquals(expectedBody, responseBody);
    }


    @Step("Send Post request")
    public static Response sendPostRequest(PostBaseModel request, String url) {
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(request)
                .when()
                .post(url);
        return response;
    }

}
