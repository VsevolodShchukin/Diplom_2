package methods;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BaseModel;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.util.ArrayList;

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

    @Step("Check field from response")
    public void checkFieldFromResponse(Response response, String path, String expectedValue) {
        Assert.assertEquals(expectedValue, response.path(path));
    }

    @Step("Check field from response")
    public void checkFieldFromResponse(Response response, String path, Boolean expectedValue) {
        Assert.assertEquals(expectedValue, response.path(path));
    }

    @Step("Check field from response")
    public void checkFieldFromResponse(Response response, String path, ArrayList expectedValue) {
        Assert.assertEquals(expectedValue, response.path(path));
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

    @Step("Send Post request")
    public static Response sendPostRequest(BaseModel request, String url, String token) {
        Response response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
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

    @Step("Send Patch request without token")
    public static Response sendPatchRequest(UserPostModel userPost, String url) {
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(userPost)
                .when()
                .patch(url);
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

    @Step("Send Get request")
    public static Response sendGetRequest(String url) {
        Response response = given()
                .spec(getBaseSpec())
                .when()
                .get(url);
        return response;
    }

    @Step("Send Delete request")
    public static Response sendDeleteRequest(String url, String token) {
        Response response = given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .and()
                .when()
                .delete(url);
        return response;
    }

    @Step("Prepare data for new user")
    public UserPostModel createNewUser() {
        String randomData = RandomStringUtils.randomAlphabetic(10);
        UserPostModel userPost = new UserPostModel(randomData + "@example.com", randomData, randomData);
        return userPost;
    }

}
