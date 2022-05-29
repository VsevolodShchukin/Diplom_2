package methods;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BaseModel;
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
    public static Response sendPostRequest(BaseModel request, String url) {
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(request)
                .when()
                .post(url);
        return response;
    }

    @Step("Send Patch request")
    public static Response sendPatchRequest(BaseModel request, String url, String token) {
        Response response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .and()
                .body(request)
                .when()
                .patch(url);
        return response;
    }

    @Step("Send Get request")
    public static Response sendGetRequest(String url) {
        Response response = given()
                .spec(getBaseSpec())
                .when()
                .get(url);
        return response;
    }

    @Step("Send Get request")
    public static Response sendGetRequest(String url, String token) {
        Response response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(url);
        return response;
    }

}
