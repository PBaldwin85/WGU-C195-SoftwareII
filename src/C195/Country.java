package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Country {

    static String[] country = {"Country 1", "Country 2"};




    public static ObservableList<String> states = FXCollections.observableArrayList(country);
}
