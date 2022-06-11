import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import methods.AuthUserMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Locale;

@RunWith(Parameterized.class)
public class ChangingUsersDataParametrizedTest {

    private final String email;
    private final String name;
    private final Boolean isAuthorized;
    UserPostModel userPost;
    String token;

    public ChangingUsersDataParametrizedTest(String newRandomData, Boolean isAuthorized) {
        this.email = newRandomData.toLowerCase(Locale.ROOT) + "@example.com";
        this.name = newRandomData;
        this.isAuthorized = isAuthorized;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        String newRandomData = RandomStringUtils.randomAlphabetic(10);
        return new Object[][]{
                {newRandomData, true},
                {newRandomData, false}
        };
    }

    AuthUserMethods authUserMethods = new AuthUserMethods();

    @Before
    public void setUp() {
        System.out.println("Set up");
        AuthRegisterMethods authRegisterMethods = new AuthRegisterMethods();
        userPost = authRegisterMethods.createNewUser();
        token = authRegisterMethods.registerNewUser(userPost);
    }

    @After
    public void tearDown() {
        System.out.println("Tear down");
        //Ручка на удаления пользака
        AuthUserMethods authUserMethods = new AuthUserMethods();
        Response response = authUserMethods.sendDeleteUserRequest(token);
        authUserMethods.checkStatusCode(response, 202);

    }

    @Test
    @DisplayName("Изменения данных пользователя")
    public void shouldChangeUsersDataTest() {

        userPost.setEmail(email);
        userPost.setName(name);

        if(isAuthorized) {
            Response responsePatch = authUserMethods.sendPatchUserRequest(userPost, token);
            authUserMethods.checkStatusCode(responsePatch, 200);
            Response responseGet = authUserMethods.sendGetUserRequest(token);
            authUserMethods.checkStatusCode(responseGet, 200);
            authUserMethods.checkFieldFromResponse(responseGet, "success", true);
            authUserMethods.checkFieldFromResponse(responseGet, "user.email", email);
            authUserMethods.checkFieldFromResponse(responseGet, "user.name", name);
        } else {
            Response responsePatch = authUserMethods.sendPatchUserRequest(userPost);
            authUserMethods.checkStatusCode(responsePatch, 401);
            authUserMethods.checkFieldFromResponse(responsePatch, "success", false);
            authUserMethods.checkFieldFromResponse(responsePatch, "message", "You should be authorised");
        }

    }

}
