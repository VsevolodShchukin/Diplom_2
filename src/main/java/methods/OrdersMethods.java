package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.OrderPostModel;



public class OrdersMethods extends BaseMethods{

    private final String url = "/api/orders";

    @Step("Send post /api/orders request")
    public Response sendPostOrderRequest(OrderPostModel order) {
        Response response = sendPostRequest(order, url);
        return response;
    }

    @Step("Send post /api/orders request with authorization token")
    public Response sendPostOrderRequest(OrderPostModel order, String token) {
        Response response = sendPostRequest(order, url, token);
        return response;
    }

    @Step("Send get /api/orders request")
    public Response sendGetOrderRequest() {
        Response response = sendGetRequest(url);
        return response;
    }

    @Step("Send get /api/orders request with authorization token")
    public Response sendGetOrderRequest(String token) {
        Response response = sendGetRequest(url, token);
        return response;
    }

}
