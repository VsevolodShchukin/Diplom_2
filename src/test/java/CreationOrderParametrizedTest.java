import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import methods.AuthUserMethods;
import methods.IngredientMethods;
import methods.OrdersMethods;
import models.OrderPostModel;
import models.UserPostModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;


@RunWith(Parameterized.class)
public class CreationOrderParametrizedTest {

    private final Boolean isAuthorized;
    private final ArrayList<String> ingredient;
    private final int statusCode;
    private final Boolean expectedValue;

    public CreationOrderParametrizedTest(Boolean isAuthorized, ArrayList<String> ingredient, int statusCode, Boolean expectedValue) {
        this.isAuthorized = isAuthorized;
        this.ingredient = ingredient;
        this.statusCode = statusCode;
        this.expectedValue = expectedValue;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        IngredientMethods ingredientMethods = new IngredientMethods();
        ArrayList<String> ingredients = ingredientMethods.sendGetIngredientRequest().path("data._id");
        ArrayList<String> ingredientsWithWrongHash = new ArrayList<String>();
        for(String ingredientHash: ingredients) {
            ingredientsWithWrongHash.add(ingredientHash + "wronghash");
        }
        return new Object[][]{
                {true, ingredients, 200, true},
                {true, null, 400, false},
                {true, ingredientsWithWrongHash, 500, null},
                {false, ingredients, 200, true},
                {false, null, 400, false},
                {false, ingredientsWithWrongHash, 500, null},
        };
    }

    String token;
    UserPostModel userPost;

    OrdersMethods ordersMethods = new OrdersMethods();

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
        authUserMethods.sendDeleteUserRequest(token);
    }

    @Test
    @DisplayName("Создание заказа авторизованным/неавторизованным пользователем")
    public void makingOrderTest() {
        OrderPostModel order = new OrderPostModel(ingredient);
        Response response;
        if(isAuthorized) {
            response = ordersMethods.sendPostOrderRequest(order, token);
        } else {
            response = ordersMethods.sendPostOrderRequest(order);
        }
        if(response.statusCode() != 500) {
            ordersMethods.checkStatusCode(response, statusCode);
            ordersMethods.checkFieldFromResponse(response, "success", expectedValue);
        } else {
            ordersMethods.checkStatusCode(response, statusCode);
        }
    }


}
