package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Contacts class used for storing the contacts from the database. */
public class Contacts {

    /** Observable list for storing all the contacts */
    public static ObservableList<String> contacts = FXCollections.observableArrayList();

    /** Populates the contact list when calling.
     * Is called from main so it's populated upon startup.
     */
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

    /** Gets the name from the contact Id that's stored in the database. */
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

    public static Integer getContactId(String passedName) throws SQLException {
        try {
            String query = "SELECT * FROM client_schedule.contacts";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String contact = result.getString("Contact_Name");
                if (contact.equals(passedName)) {
                    Integer contactId = Integer.valueOf(result.getString("Contact_ID"));
                    return contactId;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }

}
