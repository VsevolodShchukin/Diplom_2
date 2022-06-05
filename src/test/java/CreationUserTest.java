import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreationUserTest {

    AuthRegisterMethods authRegisterMethods = new AuthRegisterMethods();
    UserPostModel userPost;

    @Before
    public void setUp() {
        System.out.println("Set up");
        String randomData = RandomStringUtils.randomAlphabetic(10);
        userPost = new UserPostModel(randomData + "@example.com", randomData, randomData);
    }

    @After
    public void tearDown() {
        System.out.println("Tear down");
        //Ручка на удаления пользака
    }


    @Test
    @DisplayName("Возможно создать уникального пользователя")
    public void shouldCreateUniqueUserTest() {
        Response response = authRegisterMethods.sendPostAuthRegisterRequest(userPost);
        authRegisterMethods.checkStatusCode(response, 200);
        authRegisterMethods.checkFieldFromResponse(response, "success", true);
    }

    @Test
    @DisplayName("Невозможно создать пользователя, который уже зарегистрирован")
    public void impossibleToCreateNonUniqueUserTest() {
        Response responseBeforeCreation = authRegisterMethods.sendPostAuthRegisterRequest(userPost);
        authRegisterMethods.checkStatusCode(responseBeforeCreation, 200);
        Response responseAfterCreation = authRegisterMethods.sendPostAuthRegisterRequest(userPost);
        authRegisterMethods.checkStatusCode(responseAfterCreation, 403);
        authRegisterMethods.checkFieldFromResponse(responseAfterCreation, "success", false);
        authRegisterMethods.checkFieldFromResponse(responseAfterCreation, "message", "User already exists");

    }

}
