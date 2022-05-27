import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.PostAuthRegisterMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreationUserTest {

    PostAuthRegisterMethods methods = new PostAuthRegisterMethods();
    UserPostModel userPost;

    @Before
    public void setUp() {
        String randomData = RandomStringUtils.randomAlphabetic(10);
        userPost = new UserPostModel(randomData, randomData, randomData);
    }


    @Test
    @DisplayName("Возможно создать уникального пользователя")
    public void shouldCreateUniqueUserTest() {
        Response response = methods.sendPostAuthRegisterRequest(userPost);
        methods.checkStatusCode(response, 200);
        Assert.assertEquals(response.path("success"), true);
    }



}
