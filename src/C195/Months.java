package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static C195.Lists.appointmentList;

/** Stores all the months in a year for easy access from the reports page.
 * The month combobox accesses this to easily populate the list.
 */
public class Months {

    /** Sets the month */
    private static String month;
    /** Stores all the months into the monthList. */
    public static ObservableList<String> monthList = FXCollections.observableArrayList();

    /** Returns the month. */
    public static String getMonth() {
        return month;
    }
    /** Populates all the months when called. */
    public static void populateMonths() {
        if (monthList.contains("January")) {
            return;
        }
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
