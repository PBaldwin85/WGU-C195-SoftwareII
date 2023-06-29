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

    /**
    public static ObservableList populateTime() {


        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            time.add(String.valueOf(startTime));
            startTime = startTime.plusMinutes(15);
        }
        return null;
    }
     */

    public static void populateTimeMinusMatches(LocalTime stringToStartTime, LocalTime stringToEndTime) {

        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            if ((startTime.isAfter(stringToStartTime) || startTime.equals(stringToStartTime)) &&
                    (startTime.isBefore(stringToEndTime) ))
                {
                System.out.println(startTime);
                startTime = startTime.plusMinutes(15);
            }
            else {
                System.out.println("Missed loop: " + startTime);
                time.add(String.valueOf(startTime));
                startTime = startTime.plusMinutes(15);
            }
        }
    }

    public static void populateEndTimes(LocalTime stringToStartTime, LocalTime stringToEndTime) {

        LocalTime startTime = stringToStartTime;
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            if ((startTime.isAfter(stringToStartTime) || startTime.equals(stringToStartTime)) &&
                    (startTime.isBefore(stringToEndTime) ))
            {
                System.out.println(startTime);
                startTime = startTime.plusMinutes(15);
            }
            else {
                System.out.println("Missed loop: " + startTime);
                time.add(String.valueOf(startTime));
                startTime = startTime.plusMinutes(15);
            }
        }
    }


}
