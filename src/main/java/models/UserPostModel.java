package models;


public class UserPostModel implements PostBaseModel {

    private String email;
    private String password;
    private String name;

    public UserPostModel(String userEmail, String userPassword, String userName) {
        this.email = userEmail + "@example.com";
        this.password = userPassword;
        this.name = userName;
    }



}
