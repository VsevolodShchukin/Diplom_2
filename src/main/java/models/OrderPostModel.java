package models;

import java.util.ArrayList;

public class OrderPostModel implements BaseModel{

    private ArrayList<String> ingredients;

    public OrderPostModel(ArrayList<String> array) {
        this.ingredients = array;
    }

}
