import io.restassured.response.Response;
import methods.AuthRegisterMethods;
import methods.IngredientMethods;
import methods.OrdersMethods;
import models.OrderPostModel;
import models.UserPostModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GetOrderTest {

    String token;
    UserPostModel userPost;
    ArrayList<String> ingredient = new ArrayList<>();

    OrdersMethods ordersMethods = new OrdersMethods();

    @Before
    public void setUp() {
        System.out.println("Set up");
        AuthRegisterMethods authRegisterMethods = new AuthRegisterMethods();
        userPost = authRegisterMethods.createNewUser();
        token = authRegisterMethods.registerNewUser(userPost);

        IngredientMethods ingredientMethods = new IngredientMethods();
        ingredient.add(ingredientMethods.sendGetIngredientRequest().path("data._id[0]"));

        OrderPostModel order = new OrderPostModel(ingredient);
        ordersMethods.sendPostOrderRequest(order, token);
    }

    @After
    public void tearDown() {
        System.out.println("Tear down");
        //Ручка на удаления пользака
    }

    @Test
    public void getOrderByAuthorizedUserTest() {
        Response response = ordersMethods.sendGetOrderRequest(token);
        ordersMethods.checkStatusCode(response, 200);
        ordersMethods.checkFieldFromResponse(response, "success", true);
        ordersMethods.checkFieldFromResponse(response, "orders.ingredients[0]", ingredient);
    }

    @Test
    public void getOrderByUnauthorizedUserTest() {
        Response response = ordersMethods.sendGetOrderRequest();
        ordersMethods.checkStatusCode(response, 401);
        ordersMethods.checkFieldFromResponse(response, "success", false);
        ordersMethods.checkFieldFromResponse(response, "message", "You should be authorised");
    }

}
