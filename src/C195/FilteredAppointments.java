package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilteredAppointments {

    private static Integer appointmentId;
    private static String title;
    private static String type;
    private static String description;
    private static String startDate;
    private static String endDate;
    private static Integer customerId;


    public static ObservableList<FilteredAppointments> filteredAppointmentList = FXCollections.observableArrayList();


    public static Integer getAppointmentId() {
        return appointmentId;
    }

    public static String getTitle() {
        return title;
    }

    public static String getType() {
        return type;
    }

    public static String getDescription() {
        return description;
    }

    public static String getStartDate() {
        return startDate;
    }

    public static String getEndDate() {
        return endDate;
    }

    public static Integer getCustomerId() {
        return customerId;
    }



    public FilteredAppointments(Integer id, String title, String type, String description, String startDate, String endDate, Integer customerId) {
        this.appointmentId = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
    }

    public static ObservableList getAppointments() {
        return filteredAppointmentList;
    }
}
