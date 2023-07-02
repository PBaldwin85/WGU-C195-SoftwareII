package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilteredAppointments {

    private Integer appointmentId;
    private String title;
    private String type;
    private String description;
    private String startDate;
    private String endDate;
    private Integer customerId;


    public static ObservableList<FilteredAppointments> filteredAppointmentList = FXCollections.observableArrayList();


    public Integer getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getCustomerId() {
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


    public static void addAppointment(FilteredAppointments appointment) {
        filteredAppointmentList.add(appointment);
    }
}
