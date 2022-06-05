import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import methods.AuthLoginMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {

    AuthLoginMethods authLoginMethods = new AuthLoginMethods();
    UserPostModel userPost;

    @Before
    public void setUp() {
        System.out.println("Set up");
        AuthRegisterMethods authRegisterMethods = new AuthRegisterMethods();
        userPost = authRegisterMethods.createNewUser();
        authRegisterMethods.registerNewUser(userPost);
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
        Response response = authLoginMethods.sendPostLoginRequest(userPost);
        authLoginMethods.checkStatusCode(response, 200);
        authLoginMethods.checkFieldFromResponse(response, "success", true);
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void impossibleToLoginWithIncorrectLoginTest() {
        String randomLogin = RandomStringUtils.randomAlphabetic(10);
        userPost.setEmail(randomLogin);
        Response response = authLoginMethods.sendPostLoginRequest(userPost);
        authLoginMethods.checkStatusCode(response, 401);
        authLoginMethods.checkFieldFromResponse(response, "success", false);
        authLoginMethods.checkFieldFromResponse(response, "message", "email or password are incorrect");
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void impossibleToLoginWithIncorrectPasswordTest() {
        String randomPassword = RandomStringUtils.randomAlphabetic(10);
        userPost.setPassword(randomPassword);
        Response response = authLoginMethods.sendPostLoginRequest(userPost);
        authLoginMethods.checkStatusCode(response, 401);
        authLoginMethods.checkFieldFromResponse(response, "success", false);
        authLoginMethods.checkFieldFromResponse(response, "message", "email or password are incorrect");
    }

}
