package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Users {

    private Integer userId;
    private String userName;
    private String password;

    Users(Integer userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public static ObservableList<Users> userList = FXCollections.observableArrayList();

    public static ObservableList<Users> getUserList() {
        return userList;
    }

    public Integer getUserId() {return userId;}

    public String getUserName() {return userName;}

    public String getPassword() {return password;}

    public static void getUsers() {
        try {
            String query = "SELECT * FROM client_schedule.users;";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Integer userId = Integer.valueOf(result.getString("User_ID"));
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                Users users = new Users(userId, userName, password);
                userList.add(users);
            }

        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        }
}
