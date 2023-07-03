package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

/** Users class for storing the user login information.
 * Allows for a quick and easy check when a user attempts to login.
 */
public class Users {
    /** Stores the user ID. */
    private Integer userId;
    /** Stores the username. */
    private String userName;
    /** Stores the password. */
    private String password;

    /** Stores all the information together. */
    Users(Integer userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
    /** Stores all the users into the observable list. */
    public static ObservableList<Users> userList = FXCollections.observableArrayList();

    /** Gets the user list. */
    public static ObservableList<Users> getUserList() {
        return userList;
    }
    /** Gets the user ID. */
    public Integer getUserId() {return userId;}
    /** Gets the username. */
    public String getUserName() {return userName;}
    /** Gets the password. */
    public String getPassword() {return password;}
    /** Gets the users and passwords from the database. */
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
