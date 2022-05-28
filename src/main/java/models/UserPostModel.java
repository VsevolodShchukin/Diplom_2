package models;


public class UserPostModel implements PostBaseModel {

    private String email;
    private String password;
    private String name;

    public UserPostModel(String userEmail, String userPassword, String userName) {
        this.email = userEmail;
        this.password = userPassword;
        this.name = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
