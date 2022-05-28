import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.PostAuthRegisterMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreationUserTest {

    private final String userAlreadyExistResponseMessage = "{\"success\":false,\"message\":\"User already exists\"}";

    PostAuthRegisterMethods methods = new PostAuthRegisterMethods();
    UserPostModel userPost;

    @Before
    public void setUp() {
        String randomData = RandomStringUtils.randomAlphabetic(10);
        userPost = new UserPostModel(randomData + "@example.com", randomData, randomData);
    }


    @Test
    @DisplayName("Возможно создать уникального пользователя")
    public void shouldCreateUniqueUserTest() {
        Response response = methods.sendPostAuthRegisterRequest(userPost);
        methods.checkStatusCode(response, 200);
        Assert.assertEquals(response.path("success"), true);
    }

    @Test
    @DisplayName("Невозможно создать пользователя, который уже зарегистрирован")
    public void impossibleToCreateNonUniqueUserTest() {
        Response responseBeforeCreation = methods.sendPostAuthRegisterRequest(userPost);
        methods.checkStatusCode(responseBeforeCreation, 200);
        Response responseAfterCreation = methods.sendPostAuthRegisterRequest(userPost);
        methods.checkStatusCode(responseAfterCreation, 403);
        methods.checkBodyFromResponse(responseAfterCreation, userAlreadyExistResponseMessage);

    }

}
