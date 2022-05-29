import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.AuthLoginMethods;
import methods.AuthRegisterMethods;
import methods.AuthUserMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ChangingUsersDataTest {

    private final String email;
    private final String password;
    private final String name;
    private final Boolean isAuthorized;

    public ChangingUsersDataTest(String email, String password, String name, Boolean isAuthorized) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isAuthorized = isAuthorized;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        String newRandomData = RandomStringUtils.randomAlphabetic(10);
        return new Object[][]{
                {newRandomData, userPost.getPassword(), userPost.getName(), true},
                {userPost.getEmail(), newRandomData, userPost.getName(), true},
                {userPost.getEmail(), userPost.getPassword(), newRandomData, true},
                {newRandomData, userPost.getPassword(), userPost.getName(), false},
                {userPost.getEmail(), newRandomData, userPost.getName(), false},
                {userPost.getEmail(), userPost.getPassword(), newRandomData, false}
        };
    }


    AuthUserMethods methods = new AuthUserMethods();
    UserPostModel userPost;
    private String accessToken;

    @Before
    public void setUp() {
        System.out.println("Set up");
        String randomData = RandomStringUtils.randomAlphabetic(10);
        userPost = new UserPostModel(randomData + "@example.com", randomData, randomData);
        AuthRegisterMethods preconditionMethod = new AuthRegisterMethods();
        Response response = preconditionMethod.sendPostAuthRegisterRequest(userPost);
        preconditionMethod.checkStatusCode(response, 200);
        accessToken = response.body().path("accessToken");
        System.out.println(accessToken);
    }

    @After
    public void tearDown() {
        System.out.println("Tear down");
        //Ручка на удаления пользака
    }

    @Test
    @DisplayName("Изменения данных пользователя с авторизацией")
    public void shouldChangeUsersDataTest() {
        if(isAuthorized) {
            Response response = methods.sendPatchUserRequest(userPost, accessToken);
            methods.checkStatusCode(response, 200);
        }




    }

}
