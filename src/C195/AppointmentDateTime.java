package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.*;

public class AppointmentDateTime {

    public static ObservableList<String> time = FXCollections.observableArrayList();
    public static ObservableList<String> endTimeList = FXCollections.observableArrayList();

    private final AppointmentDateTime setTime;

    public AppointmentDateTime(AppointmentDateTime appointmentDateTime) {
        this.setTime = appointmentDateTime;
    }


    public static ObservableList<String> timeToDelete = FXCollections.observableArrayList();



    public static void populateTime(ZoneId timeZone) {
        time.clear();

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
    }


    public static void removeMatches(ZoneId timeZone, LocalTime stringToStartTime, LocalTime stringToEndTime) {
        time.clear();
        timeToDelete.clear();
        populateTime(timeZone);

        ZoneId est = ZoneId.of("America/New_York");

        ZoneOffset offset1 = ZonedDateTime.now(est).getOffset();
        ZoneOffset offset2 = ZonedDateTime.now(timeZone).getOffset();
        int offsetDiff = offset1.compareTo(offset2);


        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:00:00");

        LocalTime adjustedStartTime = startTime.plusSeconds(offsetDiff);
        LocalTime adjustedEndTime = endTime.plusSeconds(offsetDiff);




        while ((stringToStartTime.isBefore(stringToEndTime)) || (stringToStartTime.equals(stringToEndTime))) {
            if ((stringToStartTime.isAfter(stringToStartTime) || stringToStartTime.equals(stringToStartTime)) &&
                    ((stringToStartTime.isBefore(stringToEndTime))))  {
                timeToDelete.add(String.valueOf(stringToStartTime));
                stringToStartTime = stringToStartTime.plusMinutes(15);
                if (stringToStartTime.equals(adjustedEndTime)) {
                    timeToDelete.add(String.valueOf(stringToStartTime));
                    stringToStartTime = stringToStartTime.plusMinutes(15);
                }
            }



            else {
                stringToStartTime = stringToStartTime.plusMinutes(15);

            }
        }

        System.out.println(timeToDelete);
        time.removeAll(timeToDelete);



        if (time.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("There are no more appointments available for the selected day.");
            alert.showAndWait();
            return;
        }

    }

    public static void setEndTimes(ZoneId timeZone, LocalTime selectedTime, LocalTime endTimeFound, boolean needOffset) {

        ZoneId est = ZoneId.of("America/New_York");

        ZoneOffset offset1 = ZonedDateTime.now(est).getOffset();
        ZoneOffset offset2 = ZonedDateTime.now(timeZone).getOffset();
        int offsetDiff = offset1.compareTo(offset2);

        LocalTime startTime = selectedTime;
        LocalTime endTime = endTimeFound;

        if (needOffset == true) {
            endTime = endTime.plusSeconds(offsetDiff);
        }

        startTime = startTime.plusMinutes(15);

        while (startTime.isBefore(endTime) || (startTime.equals(endTime))) {
            endTimeList.add(String.valueOf(startTime));
            startTime = startTime.plusMinutes(15);

        }

    }

}
