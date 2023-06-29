package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class AppointmentDateTime {

        public static ObservableList<String> time = FXCollections.observableArrayList();
    private final AppointmentDateTime setTime;

    public AppointmentDateTime(AppointmentDateTime appointmentDateTime) {
        this.setTime = appointmentDateTime;
    }


    public static ObservableList populateTime() {


        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            time.add(String.valueOf(startTime));
            startTime = startTime.plusMinutes(15);
        }
        return null;
    }

}
