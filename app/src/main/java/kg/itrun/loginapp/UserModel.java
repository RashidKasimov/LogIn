package kg.itrun.loginapp;

public class UserModel {
    private String login;
    private String password;
    public String name;
    public String position;

    public UserModel(String login, String password, String name, String position) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.position = position;
    }

    public boolean isCredentialsValid(String login, String password) {
        return this.login.equals(login) && this.password.equals(password);
    }
}
