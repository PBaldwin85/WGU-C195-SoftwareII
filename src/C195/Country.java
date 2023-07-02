package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Country class used for storing the Countries from the database. */
public class Country {
    /** Country name */
    private static Country name;

    /** Observable list that the countries are stored in */
    public static ObservableList<String> countries = FXCollections.observableArrayList();

    /** Stores the Country name */
    public Country(Country country) {
        this.name = country;
    }
    /** Gets the Country name. */
    public static Country getName(Integer contactId) {
        return name;
    }
    /** Populates the countries from the database.
     * Called from main to populate countries at startup.
     */
    public static void populateCountries() {
        try {
            String query = "SELECT Country FROM client_schedule.countries";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String country = result.getString("Country");
                countries.add(country);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /** Gets the Country name from the Country Id that's stored in the database. */
    public static Object getCountry(Integer passedId) throws SQLException {
        try {
            String query = "SELECT * FROM client_schedule.countries";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);


            while (result.next()) {
                Integer countryId = Integer.valueOf(result.getString("Country_Id"));
                if (countryId == passedId) {
                    String country = result.getString("Country");
                    return country;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }
}
