package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentList {

    public static ObservableList<AppointmentList> appointmentList = FXCollections.observableArrayList();

    /** Stores a product into a list. */

    public static ObservableList<AppointmentList> getAppointments() {
        return appointmentList;
    }

    public static void addAppointment(Appointments appointment) {
        appointmentList.add(appointment);
    }
}
