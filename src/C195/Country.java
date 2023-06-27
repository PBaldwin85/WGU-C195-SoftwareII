package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Country {
    private static Country name;

    public static ObservableList<String> countries = FXCollections.observableArrayList();

    public Country(Country country) {
        this.name = country;
    }

    public static Country getName() {
        return name;
    }



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
