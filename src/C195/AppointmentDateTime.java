package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentDateTime {

    public static ObservableList<String> time = FXCollections.observableArrayList();
    public static ObservableList<String> endTimeList = FXCollections.observableArrayList();

    private final AppointmentDateTime setTime;

    public AppointmentDateTime(AppointmentDateTime appointmentDateTime) {
        this.setTime = appointmentDateTime;
    }


    public static ObservableList<String> timeToDelete = FXCollections.observableArrayList();



    public static ObservableList<String> populateTime(ZoneId timeZone) {
        ZoneId est = ZoneId.of("America/New_York");

        ZoneOffset offset1 = ZonedDateTime.now(est).getOffset();
        ZoneOffset offset2 = ZonedDateTime.now(timeZone).getOffset();
        int offsetDiff = offset1.compareTo(offset2);

        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:01:00");

        LocalTime adjustedStartTime = startTime.plusSeconds(offsetDiff);
        LocalTime adjustedEndtTime = endTime.plusSeconds(offsetDiff);


        while (adjustedStartTime.isBefore(adjustedEndtTime)) {
            time.add(String.valueOf(adjustedStartTime));
            adjustedStartTime = adjustedStartTime.plusMinutes(15);
        }
        return null;
    }


    public static void removeMatches(ZoneId timeZone, LocalTime stringToStartTime, LocalTime stringToEndTime) {
        ZoneId est = ZoneId.of("America/New_York");

        ZoneOffset offset1 = ZonedDateTime.now(est).getOffset();
        ZoneOffset offset2 = ZonedDateTime.now(timeZone).getOffset();
        int offsetDiff = offset1.compareTo(offset2);


        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:01:00");

        LocalTime adjustedStartTime = startTime.plusSeconds(offsetDiff);
        LocalTime adjustedEndtTime = endTime.plusSeconds(offsetDiff);

        while (adjustedStartTime.isBefore(adjustedEndtTime)) {
            if ((adjustedStartTime.isAfter(stringToStartTime) || adjustedStartTime.equals(stringToStartTime)) &&
                    (adjustedStartTime.isBefore(stringToEndTime))) {
                timeToDelete.add(String.valueOf(adjustedStartTime));
                adjustedStartTime = adjustedStartTime.plusMinutes(15);
            } else {

                adjustedStartTime = adjustedStartTime.plusMinutes(15);

            }
        }
        time.removeAll(timeToDelete);


    }

    public static LocalTime getEndTime(LocalTime selectedStartTime, LocalTime stringToEndTime) {

        LocalTime startTime = selectedStartTime;
        LocalTime endTime = LocalTime.parse("22:01:00");

        while (startTime.isBefore(endTime)) {
            if (startTime.equals(stringToEndTime)) {
                return stringToEndTime;
            } else {
                startTime = startTime.plusMinutes(15);

            }
        }
        return null;
    }


    public static void setEndTimes(LocalTime selectedTime, LocalTime endTimeFound) {
        if (!endTimeList.isEmpty()) {
            return;
        }
        else {

            LocalTime startTime = selectedTime;
            LocalTime endTime = endTimeFound;
            startTime = startTime.plusMinutes(15);


            while (startTime.isBefore(endTime) || (startTime.equals(endTime))) {
                endTimeList.add(String.valueOf(startTime));
                startTime = startTime.plusMinutes(15);
            }
        }

    }
}
