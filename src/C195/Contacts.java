package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Contacts {

    private static Contacts name;


    public static ObservableList<String> contacts = FXCollections.observableArrayList();

    public static Contacts getName() {
        return name;
    }

    public static void populateNames() {
        try {
            String query = "SELECT * FROM client_schedule.contacts";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String name = result.getString("Contact_Name");
                contacts.add(name);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static Object getName(Integer passedId) throws SQLException {
        try {
            String query = "SELECT * FROM client_schedule.contacts";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);


            while (result.next()) {
                Integer contactId = Integer.valueOf(result.getString("Contact_ID"));
                if (contactId == passedId) {
                    String contactName = result.getString("Contact_Name");
                    return contactName;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }

}
