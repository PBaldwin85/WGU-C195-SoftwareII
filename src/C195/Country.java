package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
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
}
