package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** FilteredAppointments class used for the reports page.
 * Pulls the selected Contact and stores all their appointments into the filteredAppointmentList.
 * The table then populates based on that list.
 */
public class FilteredAppointments {

    private Integer appointmentId;
    private String title;
    private String type;
    private String description;
    private String startDate;
    private String endDate;
    private Integer customerId;

    /** filteredAppointmentList Observable list for the reports page. */
    public static ObservableList<FilteredAppointments> filteredAppointmentList = FXCollections.observableArrayList();

    /** Gets the appointment ID. */
    public Integer getAppointmentId() {
        return appointmentId;
    }
    /** Gets the appointment title. */
    public String getTitle() {
        return title;
    }
    /** Gets the appointment type. */
    public String getType() {
        return type;
    }
    /** Gets the appointment description. */
    public String getDescription() {
        return description;
    }
    /** Gets the appointment start date and time. */
    public String getStartDate() {
        return startDate;
    }
    /** Gets the appointment end date and time. */
    public String getEndDate() {
        return endDate;
    }
    /** Gets the customer ID. */
    public Integer getCustomerId() {
        return customerId;
    }
    /** Sets all the filetered appointments prior to storing into the observable list. */
    public FilteredAppointments(Integer id, String title, String type, String description, String startDate, String endDate, Integer customerId) {
        this.appointmentId = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
    }
    /** Returns the observable list. */
    public static ObservableList getAppointments() {
        return filteredAppointmentList;
    }
    /** Adds an appointment. */
    public static void addAppointment(FilteredAppointments appointment) {
        filteredAppointmentList.add(appointment);
    }
}
