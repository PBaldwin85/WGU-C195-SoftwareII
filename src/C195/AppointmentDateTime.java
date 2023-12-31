package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.*;

/** AppointmentDateTime class
 * Used for setting times start and end times that are available.
 */
public class AppointmentDateTime {

    /** Used for storing the start times list. */
    public static ObservableList<String> time = FXCollections.observableArrayList();

    /** Used for storing the end time selection list. */
    public static ObservableList<String> endTimeList = FXCollections.observableArrayList();

    /** Used for storing the times that can't be selected from the time list. */
    public static ObservableList<String> timeToDelete = FXCollections.observableArrayList();

    /** Populates all the start times and converts the times based on the users timezone. */
    public static void populateTime(ZoneId timeZone) {
        time.clear();

        ZoneId est = ZoneId.of("America/New_York");

        ZoneOffset offset1 = ZonedDateTime.now(est).getOffset();
        ZoneOffset offset2 = ZonedDateTime.now(timeZone).getOffset();
        int offsetDiff = offset1.compareTo(offset2);

        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime = LocalTime.parse("22:00:00");

        LocalTime newDay = LocalTime.parse("12:00:00");

        LocalTime adjustedStartTime = startTime.plusSeconds(offsetDiff);
        LocalTime adjustedEndtTime = endTime.plusSeconds(offsetDiff);

        if (adjustedStartTime.equals(newDay) || adjustedStartTime.isAfter((newDay))) {
            while (adjustedStartTime.isAfter(adjustedEndtTime)) {
                time.add(String.valueOf(adjustedStartTime));
                adjustedStartTime = adjustedStartTime.plusMinutes(15);
            }
        }

        while (adjustedStartTime.isBefore(adjustedEndtTime)) {
            time.add(String.valueOf(adjustedStartTime));
            adjustedStartTime = adjustedStartTime.plusMinutes(15);
        }
    }

    /** Removes matches from the start time based on the customer and the seleced date.
     * If a customer has an appointment scheduled on the selected date, then any matches are removed to
     * not allow overlapping appointments. */
    public static void removeMatches(ZoneId timeZone, LocalTime stringToStartTime, LocalTime stringToEndTime) {
        time.clear();
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
            if (stringToStartTime.isBefore(stringToEndTime))  {
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
        time.removeAll(timeToDelete);
        if (time.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("There are no more appointments available for the selected customer on the selected day.");
            alert.showAndWait();
            time.clear();
            return;
        }
    }

    /** Converts the users selected date and time from their timezone to eastern time zone.
     * Used for checking days and dates from time zone differences and giving the information to the user.
     * @return Returns the converted date and time.
     */
    public static LocalDateTime convertToEastern(ZoneId timeZone, LocalDateTime selectedTime) {
        ZoneId est = ZoneId.of("America/New_York");

        ZonedDateTime zonedSelectedTime = ZonedDateTime.of(selectedTime, timeZone);
        ZonedDateTime convertedTime = zonedSelectedTime.withZoneSameInstant(est);

        return convertedTime.toLocalDateTime();

    }

    public static LocalDateTime convertToUtc(ZoneId timeZone, LocalDateTime selectedTime) {
        ZoneId utc = ZoneId.of("UTC");

        ZonedDateTime zonedSelectedTime = ZonedDateTime.of(selectedTime, timeZone);
        ZonedDateTime convertedTime = zonedSelectedTime.withZoneSameInstant(utc);

        return convertedTime.toLocalDateTime();

    }

    /** Sets the end time list.
     * Sets the times based off of the selected start time and what's available for the selected customer.
     * @param timeZone Passes through the users timezone for time conversion.
     * @param selectedTime The selected start time
     * @param endTimeFound Passes an end time that needs to be set based on the currently scheduled appointments.
     * @param needOffset Checks to see if the time needs converting due to a time zone difference.
     */
    public static void setEndTimes(ZoneId timeZone, LocalTime selectedTime, LocalTime existingStartTime, boolean needOffset) {

        ZoneId est = ZoneId.of("America/New_York");

        ZoneOffset offset1 = ZonedDateTime.now(est).getOffset();
        ZoneOffset offset2 = ZonedDateTime.now(timeZone).getOffset();
        int offsetDiff = offset1.compareTo(offset2);

        LocalTime newDay = LocalTime.parse("12:00:00");
        LocalTime endDay = LocalTime.parse("22:00");

        LocalTime startTime = selectedTime;
        LocalTime endTime = existingStartTime;

        startTime = startTime.plusMinutes(15);
        endDay = endDay.plusSeconds(offsetDiff);

        if (needOffset == true) {
            endTime = endTime.plusSeconds(offsetDiff);
        }

        if (startTime.isAfter(endTime)) {
            while (startTime.isAfter(endTime) || (startTime.equals(endTime))) {
                endTimeList.add(String.valueOf(startTime));
                startTime = startTime.plusMinutes(15);
            }
            if (startTime.isBefore(endTime)) {
                while (startTime.isBefore(endTime) || (startTime.equals(endTime))) {
                    endTimeList.add(String.valueOf(startTime));
                    startTime = startTime.plusMinutes(15);
                }
            }
            return;
        }
        LocalTime startTimeCheck = LocalTime.parse("00:00:00");

        while (startTime.isBefore(endTime) || (startTime.equals(endTime))) {
            if (startTime.equals(endDay)) {
                endTimeList.add(String.valueOf(startTime));
                break;
            }
            endTimeList.add(String.valueOf(startTime));
            startTime = startTime.plusMinutes(15);

        }
    }



}
