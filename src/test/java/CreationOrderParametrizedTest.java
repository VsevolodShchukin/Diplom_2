import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import methods.IngredientMethods;
import methods.OrdersMethods;
import models.OrderPostModel;
import models.UserPostModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;


@RunWith(Parameterized.class)
public class CreationOrderParametrizedTest {


    private final ArrayList<String> ingredient;
    private final int statusCode;
    String token;
    UserPostModel userPost;


    public CreationOrderParametrizedTest(ArrayList<String> ingredient, int statusCode) {
        this.ingredient = ingredient;
        this.statusCode = statusCode;
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
                {ingredients, 200},
                {null, 400},
                {ingredientsWithWrongHash, 500}
        };
    }

    OrdersMethods ordersMethods = new OrdersMethods();

    @After
    public void tearDown() {
        System.out.println("Tear down");
        //Ручка на удаления пользака
    }

    @Test
    public void makingOrderWithAuthorizedTest() {
        AuthRegisterMethods authRegisterMethods = new AuthRegisterMethods();
        userPost = authRegisterMethods.createNewUser();
        token = authRegisterMethods.registerNewUser(userPost);

        OrderPostModel order = new OrderPostModel(ingredient);
        Response response = ordersMethods.sendPostOrderRequest(order, token);
        ordersMethods.checkStatusCode(response, statusCode);
    }

    @Test
    public void makingOrderWithoutAuthorizedTest() {
        OrderPostModel order = new OrderPostModel(ingredient);
        Response response = ordersMethods.sendPostOrderRequest(order);
        ordersMethods.checkStatusCode(response, statusCode);
    }

    //Необходимо додумать, как проверять тела ответов у парам. тестов

}
