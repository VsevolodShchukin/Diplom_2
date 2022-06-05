import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreationUserParametrizedTest {

    private final String email;
    private final String password;
    private final String name;

    public CreationUserParametrizedTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        String randomData = RandomStringUtils.randomAlphabetic(10);
        return new Object[][]{
                {null, randomData, randomData},
                {randomData, null, randomData},
                {randomData, randomData, null}
        };
    }

    AuthRegisterMethods authRegisterMethods = new AuthRegisterMethods();


    @Test
    @DisplayName("Невозможно не заполнить одно из обязательных полей и создать пользователя")
    public void impossibleToCreateUserWithoutRequiredFieldTest() {
        UserPostModel userPost = new UserPostModel(email, password, name);
        Response response = authRegisterMethods.sendPostAuthRegisterRequest(userPost);
        authRegisterMethods.checkStatusCode(response, 403);
        authRegisterMethods.checkFieldFromResponse(response, "success", false);
        authRegisterMethods.checkFieldFromResponse(response, "message", "Email, password and name are required fields");

    }

}
