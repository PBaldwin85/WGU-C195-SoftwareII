package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class AppointmentDateTime {

    public static ObservableList<String> time = FXCollections.observableArrayList();
    public static ObservableList<String> endTimeList = FXCollections.observableArrayList();

    private final AppointmentDateTime setTime;

    public AppointmentDateTime(AppointmentDateTime appointmentDateTime) {
        this.setTime = appointmentDateTime;
    }


    public static ObservableList<String> timeToDelete = FXCollections.observableArrayList();

    public static ObservableList populateTime() {


        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            time.add(String.valueOf(startTime));
            startTime = startTime.plusMinutes(15);
        }
        return null;
    }


    public static void removeMatches(LocalTime stringToStartTime, LocalTime stringToEndTime) {

        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            if ((startTime.isAfter(stringToStartTime) || startTime.equals(stringToStartTime)) &&
                    (startTime.isBefore(stringToEndTime) ))
                {
                timeToDelete.add(String.valueOf(startTime));
                startTime = startTime.plusMinutes(15);
            }
            else {

                time.add(String.valueOf(startTime));
                startTime = startTime.plusMinutes(15);

            }
        }
        time.removeAll(timeToDelete);
    }

    public static LocalTime getEndTime(LocalTime selectedStartTime, LocalTime stringToEndTime) {

        LocalTime startTime = selectedStartTime;
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            if (startTime.equals(stringToEndTime))
            {
                System.out.println("Start time equals end time: " + stringToEndTime);
                return stringToEndTime;
            }
            else {
                startTime = startTime.plusMinutes(15);

            }
        }
        return null;
    }


    public static void setEndTimes(LocalTime selectedTime, LocalTime endTimeFound) {

        LocalTime startTime = selectedTime;
        LocalTime endTime = endTimeFound;

        System.out.println("setEndTimes loop: " + startTime);
        System.out.println("setEndTimes loop: " + endTime);


            while (startTime.isBefore(endTime)) {
                System.out.println("setEndTimes loop: " + startTime);
                endTimeList.add(String.valueOf(startTime));
                startTime = startTime.plusMinutes(15);
            }
        }

}
