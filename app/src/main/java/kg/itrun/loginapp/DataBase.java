package kg.itrun.loginapp;

import java.util.ArrayList;

public class DataBase {
    private ArrayList<UserModel> users;

    public DataBase() {
        users = new ArrayList<>();
        users.add(new UserModel("user1", "pass1", "Stan Smith", "Admin"));
        users.add(new UserModel("user2", "pass2", "Aibek Akimov", "Manager"));
        users.add(new UserModel("user3", "pass3", "John Doe", "Clerk"));
    }

    public UserModel getUserFromDB(String login, String password){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isCredentialsValid(login, password)) {
                return users.get(i);
            }
        }
        return null;
    }

    public void addUser(UserModel userModel) {
        users.add(userModel);
    }

}
