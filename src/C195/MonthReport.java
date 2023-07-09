package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MonthReport {

    private String month;
    private String type;
    private Integer appointments;

    /** filteredAppointmentList Observable list for the reports page. */
    public static ObservableList<MonthReport> monthTypeList = FXCollections.observableArrayList();

    /** Gets the appointment ID.
     * @return*/
    public String getMonth() {
        return month;
    }
    /** Gets the appointment type. */
    public String getType() {
        return type;
    }

    public Integer getAppointments() {return appointments;}
    /** Gets the appointment description. */
    /** Sets all the filetered appointments prior to storing into the observable list. */
    public MonthReport(String month, String type, Integer appointments) {
        this.month = month;
        this.type = type;
        this.appointments = appointments;
    }
    /** Returns the observable list. */
    public static ObservableList getMonths() {
        return monthTypeList;
    }
    /** Adds an appointment. */
    public static void addMonth(MonthReport monthReport) {
        monthTypeList.add(monthReport);
    }
}

