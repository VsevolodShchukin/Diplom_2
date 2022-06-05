package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class IngredientMethods extends BaseMethods {

    private final String url = "/api/ingredients";

    @Step("Send post /api/ingredients request")
    public Response sendGetIngredientRequest() {
        Response response = sendGetRequest(url);
        return response;
    }

}
