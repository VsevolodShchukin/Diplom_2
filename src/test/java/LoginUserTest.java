import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import methods.AuthLoginMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {

    AuthLoginMethods methods = new AuthLoginMethods();
    UserPostModel userPost;

    private final String incorrectLoginOrPasswordResponseMessage = "{\"success\":false,\"message\":\"email or password are incorrect\"}";

    @Before
    public void setUp() {
        System.out.println("Set up");
        String randomData = RandomStringUtils.randomAlphabetic(10);
        userPost = new UserPostModel(randomData + "@example.com", randomData, randomData);
        AuthRegisterMethods preconditionMethod = new AuthRegisterMethods();
        Response response = preconditionMethod.sendPostAuthRegisterRequest(userPost);
        preconditionMethod.checkStatusCode(response, 200);
        //формат тела близок к телу запроса /api/auth/register
        //Решил не создавать отдельный класс для Login, а просто удалить 1 поле после регистрации
        //для запроса авторизации
        userPost.setName(null);
    }

    @After
    public void tearDown() {
        System.out.println("Tear down");
        //Ручка на удаления пользака
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void shouldLoginByExistUserTest() {
        Response response = methods.sendPostLoginRequest(userPost);
        methods.checkStatusCode(response, 200);
        Assert.assertEquals(response.path("success"), true);
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void impossibleToLoginWithIncorrectLoginTest() {
        String randomLogin = RandomStringUtils.randomAlphabetic(10);
        userPost.setEmail(randomLogin);
        Response response = methods.sendPostLoginRequest(userPost);
        methods.checkStatusCode(response, 401);
        methods.checkBodyFromResponse(response, incorrectLoginOrPasswordResponseMessage);
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void impossibleToLoginWithIncorrectPasswordTest() {
        String randomPassword = RandomStringUtils.randomAlphabetic(10);
        userPost.setPassword(randomPassword);
        Response response = methods.sendPostLoginRequest(userPost);
        methods.checkStatusCode(response, 401);
        methods.checkBodyFromResponse(response, incorrectLoginOrPasswordResponseMessage);
    }

}
