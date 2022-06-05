import io.qameta.allure.junit4.DisplayName;
import methods.AuthRegisterMethods;
import methods.AuthUserMethods;
import models.UserPostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
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
    AuthRegisterMethods authRegisterMethods = new AuthRegisterMethods();


    @After
    public void tearDown() {
        System.out.println("Tear down");
        //Ручка на удаления пользака
    }

    @Test
    @DisplayName("Изменения данных пользователя с авторизацией")
    public void shouldChangeUsersDataTest() {
        //Создаем и регистрируем пользователя, сохраняем токен
        userPost = authRegisterMethods.createNewUser();
        String accessToken = authRegisterMethods.registerNewUser(userPost);

        //Отправляем PATH запросы и проверяем изменения
        userPost.setEmail(email);
        authUserMethods.sendPatchUserRequestAndCheckTheResponseBody(userPost, accessToken, isAuthorized, "email", userPost.getEmail());

        userPost.setName(name);
        authUserMethods.sendPatchUserRequestAndCheckTheResponseBody(userPost, accessToken, isAuthorized, "name", userPost.getName());
    }

}
