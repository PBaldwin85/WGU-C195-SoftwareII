package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** States class used for storing all the States and Provinces.
 */
public class States {
    /** Sets the State/Province name */
    private static States name;
    /** Observable list for storing all the States. */
    public static ObservableList<String> states = FXCollections.observableArrayList();
    /** Gets the State/Province name. */
    public static States getName() {
        return name;
    }
    /** Populates all the States from the database into the states observable list.
     */
    public static void populateStates() {
        try {
            String query = "SELECT DIVISION FROM client_schedule.first_level_divisions;";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String state = result.getString("Division");
                states.add(state);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /** Gets the division (State/Province) from the passed through divison ID.
     */
    public static Object getDivision(Integer passedId) throws SQLException {
        try {
            String query = "SELECT * FROM client_schedule.first_level_divisions;";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);


            while (result.next()) {
                Integer divisionId = Integer.valueOf(result.getString("Division_Id"));
                if (divisionId == passedId) {
                    String division = result.getString("Division");
                    return division;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }

    public static String getDivisionId(String passedDivision) throws SQLException {
        try {
            String query = "SELECT * FROM client_schedule.first_level_divisions";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);


            while (result.next()) {
                String division = result.getString("Division");
                if (division.equals(passedDivision)) {
                    String divisionId = result.getString("Division_ID");
                    return divisionId;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }
    /** Gets the Country ID from the passed through divison ID for filtering the State/Province list. */
    public static Object getCountryId(Integer passedId) throws SQLException {
        try {
            String query = "SELECT * FROM client_schedule.first_level_divisions;";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);


            while (result.next()) {
                Integer divisionId = Integer.valueOf(result.getString("Division_Id"));
                if (divisionId == passedId) {
                    Integer division = Integer.valueOf(result.getString("Country_ID"));
                    return division;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }

    /** Get the states from selected Country. */
    public static void getStates(Integer passedCountryId) throws SQLException {
        try {
            String query = "SELECT * FROM client_schedule.first_level_divisions;";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Integer countryId = Integer.valueOf(result.getString("Country_ID"));
                if (countryId == passedCountryId) {
                    String state = result.getString("Division");
                    states.add(state);
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
