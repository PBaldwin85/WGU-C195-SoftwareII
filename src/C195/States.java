package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class States {
    private static States name;


    public static ObservableList<String> states = FXCollections.observableArrayList();

    public static States getName() {
        return name;
    }



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

}
