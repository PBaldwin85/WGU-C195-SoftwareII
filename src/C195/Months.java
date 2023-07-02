package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static C195.Lists.appointmentList;

public class Months {

    private static String month;


    public static ObservableList<String> monthList = FXCollections.observableArrayList();

    public static String getMonth() {
        return month;
    }

    public static void populateMonths() {
        monthList.clear();
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");

    }
}
