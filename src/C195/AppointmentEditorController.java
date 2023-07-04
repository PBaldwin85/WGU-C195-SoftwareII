package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static C195.Lists.appointmentList;
import static C195.Lists.customerList;

/** AppointmentEditorController class.
 * Used for adding and editing appointments.
 */
public class AppointmentEditorController implements Initializable {
    /** Anchorpane for the window */
    public AnchorPane mainWindow;
    /** AppointmentId textfield. */
    public TextField AppointmentIdField;
    /** Title textfield. */
    public TextField titleField;
    /** Description textfield. */
    public TextField descriptionField;
    /** Location textfield. */
    public TextField locationField;
    /** Type textfield. */
    public TextField typeField;
    /** Contacts combobox. */
    public ComboBox contactsBox;
    /** Start time combobox. */
    @FXML
    private ComboBox startTime;
    /** End Time combobox. */
    @FXML
    private ComboBox endTime;
    /** User Id combobox. */
    @FXML
    private ComboBox userBox;
    /** Customer Id combobox. */
    @FXML
    private ComboBox customerBox;

    /** Saves the Appointment Id. */
    Integer savedId;

    /** Start date datepicker. */
    @FXML
    private DatePicker selectDate;
    /** End date datepicker. */
    @FXML
    private DatePicker selectEndDate;

    /** Stores the full start date and time after selecting a date and time. */
    private LocalDateTime dateTimeMerge;
    /** Stores the full end date and time after selecting a date and time. */
    private LocalDateTime endTimeMerge;

    public LocalTime startTemp;


    /** Holds the appointment ID value. */
    public static int appointment;

    /** Sets the appointment ID value. */
    public static void AppointmentId(int num) {
        appointment = num;
    }

    /** Returns the appoitment ID. */
    public static int getAppointmentId() {
        return appointment;
    }

    /** Initializes all the information upon loading.
     * Sets all the ComboBoxes and textfields if an appointment is being modified.
     * Also holds all the Lambda on action expressions.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsBox.setItems(Contacts.contacts);
        customerBox.setItems(Customers.getCustomerNames());
        userBox.setItems(Lists.users);




        /** Lamba expression used for setting times when a date is selected.
         * When a date is selected, I have it load only the available times.
         */
        selectDate.setOnAction(event -> {
            LocalDate selectedDate = selectDate.getValue();
            DayOfWeek selectedDay = selectedDate.getDayOfWeek();
            AppointmentDateTime.populateTime(ZoneId.systemDefault());
            AppointmentDateTime.timeToDelete.clear();
            AppointmentDateTime.endTimeList.clear();

            /**
            if (selectedDay == DayOfWeek.SATURDAY || selectedDay == DayOfWeek.SUNDAY) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Weekends are not allowed! Please select a weekday.");
                alert.showAndWait();
                return;
            }
             */

            selectEndDate.setValue(selectedDate);

            for (Appointments existing : appointmentList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
                LocalDateTime endDate = LocalDateTime.parse(existing.getEndDate(), formatter);
                LocalDate stringToStartDate = startDate.toLocalDate();
                LocalTime stringToStartTime = startDate.toLocalTime();
                LocalDate stringToEndDate = endDate.toLocalDate();
                LocalTime stringToEndTime = endDate.toLocalTime();

                try {
                    if (selectDate.getValue().isEqual(stringToStartDate) && (customerBox.getValue().equals(existing.getCustomerId()))) {
                        AppointmentDateTime.removeMatches(ZoneId.systemDefault(), stringToStartTime, stringToEndTime);
                    }
                }
                catch (NullPointerException e) {
                    return;
                }
            }
        });

        AppointmentDateTime.timeToDelete.clear();
        startTime.setItems(AppointmentDateTime.time);
        endTime.setItems(AppointmentDateTime.time);

        /** Lamba expression used for setting times when a customer is selected.
         * When a customer is selected, the times are filtered for that particular customer.
         * Without this, the times wouldn't repopulate.
         */
        customerBox.setOnAction(event -> {
            AppointmentDateTime.populateTime(ZoneId.systemDefault());
            AppointmentDateTime.timeToDelete.clear();
            AppointmentDateTime.endTimeList.clear();
            if (selectDate.getValue() == null) {
                return;
            } else {
                for (Appointments existing : appointmentList) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
                    LocalDateTime endDate = LocalDateTime.parse(existing.getEndDate(), formatter);
                    LocalDate stringToStartDate = startDate.toLocalDate();
                    LocalTime stringToStartTime = startDate.toLocalTime();
                    LocalDate stringToEndDate = endDate.toLocalDate();
                    LocalTime stringToEndTime = endDate.toLocalTime();

                    if (selectDate.getValue().isEqual(stringToStartDate) && (customerBox.getValue() == existing.getCustomerId())) {
                        AppointmentDateTime.removeMatches(ZoneId.systemDefault(), stringToStartTime, stringToEndTime);
                        startTime.setItems(AppointmentDateTime.time);
                    }
                }
            }

        });


        /** Lamba expression used for setting the appointment end times.
         * When a time is selected, the end time selection is populated based on the current time.
         * This allows for no overlaps in scheduling for the customer as only available times are loaded.
         */
        startTime.setOnAction(event -> {
            if (startTime.getValue() == null) {
                return;
            }
            boolean appointmentsAfter = false;
            LocalDate selectedDate = selectDate.getValue();
            LocalTime selectedTime = LocalTime.parse((CharSequence) startTime.getValue());
            startTemp = selectedTime;
            dateTimeMerge = LocalDateTime.of(selectedDate, selectedTime);

            boolean earliestSet = false;
            LocalTime earliest = null;

            for (Appointments existing : appointmentList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime existingStartDateTime = LocalDateTime.parse(existing.getStartDate(), formatter);
                LocalDateTime existingEndDateTime = LocalDateTime.parse(existing.getEndDate(), formatter);
                LocalDate existingStartDate = existingStartDateTime.toLocalDate();
                LocalTime existingStartTime = existingStartDateTime.toLocalTime();

                if (selectDate.getValue().isEqual(existingStartDate) && (customerBox.getValue() == existing.getCustomerId())) {
                    if (existingStartTime.isAfter(selectedTime)) {
                        appointmentsAfter = true;
                    }
                    if (selectDate.getValue().isEqual(existingStartDate) && selectedTime.isBefore(LocalTime.from(existingStartTime))) {
                        LocalTime test = existingStartTime;
                        AppointmentDateTime.endTimeList.clear();
                        boolean needOffset = false;
                        if (earliestSet==true) {
                            if (existingStartTime.isAfter(earliest)) {
                                AppointmentDateTime.setEndTimes(ZoneId.systemDefault(), selectedTime, earliest, needOffset);
                                endTime.setItems(AppointmentDateTime.endTimeList);
                                break;
                            }
                        }

                        AppointmentDateTime.setEndTimes(ZoneId.systemDefault(), selectedTime, existingStartTime, needOffset);
                        earliestSet = true;
                        earliest = existingStartTime;
                        endTime.setItems(AppointmentDateTime.endTimeList);
                    }
                }
            }
            if (appointmentsAfter == false) {
                AppointmentDateTime.endTimeList.clear();
                boolean needOffset = true;
                AppointmentDateTime.setEndTimes(ZoneId.systemDefault(), selectedTime, LocalTime.parse("22:00:01"), needOffset);
                endTime.setItems(AppointmentDateTime.endTimeList);
            }

        });

        /** Lamba on action expression used for storing the selected time.
         * When a time is selcted, the selected end date and time is stored.
         */
        endTime.setOnAction(event -> {
            if (endTime.getValue() == null) {
                return;
            }
            AppointmentDateTime.timeToDelete.clear();
            LocalDate selectedDate = selectDate.getValue();
            LocalTime selectedTime = LocalTime.parse((CharSequence) endTime.getValue());
            LocalTime newDay = LocalTime.parse("00:00:00");

            if (selectedTime.isBefore(startTemp)) {
                selectedDate = selectedDate.plusDays(1);
                selectEndDate.setValue(selectedDate);
                }
            else {
                selectEndDate.setValue(selectedDate);
            }
            endTimeMerge = LocalDateTime.of(selectedDate, selectedTime);

            LocalDateTime convertedToEastern = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),endTimeMerge);
            DayOfWeek convertedDay = convertedToEastern.getDayOfWeek();
            if (convertedDay == DayOfWeek.SATURDAY || convertedDay == DayOfWeek.SUNDAY) {
                String pattern = "yyyy-MM-dd HH:mm:ss";
                DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
                String startTimeEasternText = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),dateTimeMerge).format(test);
                String endTimeEasternText = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),endTimeMerge).format(test);

                String invalidDay = "Weekends are not allowed. Please select a weekday.\n";
                invalidDay += "The appointment in Eastern Standard Time is the following:\n";
                invalidDay += "Start date and time: " + convertedDay + " " + startTimeEasternText + "\n";
                invalidDay += "End date and time: " + convertedDay + " " +endTimeEasternText + "\n";
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText(invalidDay);
                alert.showAndWait();

                return;
            }

        });

    }

    /** Saves all the entered information.
     * If there is information missing before the save, errors are thrown and caught,
     * letting the user know what they're missing. */
    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {
            Integer appointmentId = Integer.valueOf(AppointmentIdField.getText());
            String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            String contact = String.valueOf(contactsBox.getValue());
            String type = typeField.getText();
            Integer customerId = (Integer) customerBox.getValue();
            Integer userId = (Integer) userBox.getValue();

            if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() || locationField.getText().isEmpty()
                    || contactsBox.getSelectionModel().isEmpty() || typeField.getText().isEmpty()
                    || (customerBox.getValue() == null) || (userBox.getValue() == null) || (selectDate.getValue() == null)
                    || (startTime.getValue() == null) || (endTime.getValue() == null))
            {
                throw new NumberFormatException();
            }

            LocalDateTime convertedToEastern = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),endTimeMerge);
            DayOfWeek convertedDay = convertedToEastern.getDayOfWeek();
            if (convertedDay == DayOfWeek.SATURDAY || convertedDay == DayOfWeek.SUNDAY) {
                throw new IOException();
            }

            String pattern = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
            String formattedStartDateTime = dateTimeMerge.format(test);
            String formattedEndDateTime = endTimeMerge.format(test);

            Appointments appointment = new Appointments(appointmentId, title, description, location, contact, type, formattedStartDateTime, formattedEndDateTime, customerId, userId);
            updateAppointment(appointment);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            String startTimeEasternText = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),dateTimeMerge).format(test);
            String endTimeEasternText = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),endTimeMerge).format(test);

            String informationText = "The appointment has been saved. \n";
            informationText += "The appointment in Eastern Standard Time is the following:\n";
            informationText += "Start date and time: " + startTimeEasternText + "\n";
            informationText += "End date and time: " + endTimeEasternText + "\n";
            alert.setContentText(informationText);
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            Parent addPartParent = loader.load();
            Scene scene = new Scene(addPartParent);
            stage.setScene(scene);
            stage.show();
        }
    catch(NumberFormatException | IOException e) {
        String invalidFields = "The following fields have errors:\n";
        if (titleField.getText().isEmpty()) {
            invalidFields += "Title is empty\n";
        }
        if (descriptionField.getText().isEmpty()) {
            invalidFields += "Description is empty\n";
        }
        if (locationField.getText().isEmpty()) {
            invalidFields += "Location is empty\n";
        }
        if (contactsBox.getSelectionModel().isEmpty()) {
            invalidFields += "Contact is empty\n";
        }
        if (typeField.getText().isEmpty()) {
            invalidFields += "Type is empty\n";
        }
        if (customerBox.getValue() == null) {
            invalidFields += "Customer ID is empty\n";
        }
        if (userBox.getValue() == null) {
            invalidFields += "User ID is empty\n";
        }
        if (selectDate.getValue() == null) {
            invalidFields += "Start date is empty\n";
        }
        if (startTime.getValue() == null) {
            invalidFields += "Start time is empty\n";
        }
        if (endTime.getValue() == null) {
            invalidFields += "End time is empty\n";
        }
        if (selectDate.getValue() != null) {
            LocalDateTime convertedToEastern = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),endTimeMerge);
            DayOfWeek convertedDay = convertedToEastern.getDayOfWeek();
            if (convertedDay == DayOfWeek.SATURDAY || convertedDay == DayOfWeek.SUNDAY) {
                invalidFields += "Please select a weekday\n";
                String pattern = "yyyy-MM-dd HH:mm:ss";
                DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
                String startTimeEasternText = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),dateTimeMerge).format(test);
                String endTimeEasternText = AppointmentDateTime.convertToEastern(ZoneId.systemDefault(),endTimeMerge).format(test);

                invalidFields += "The appointment in Eastern Standard Time is the following:\n";
                invalidFields += "Start date and time: " + convertedDay + " " + startTimeEasternText + "\n";
                invalidFields += "End date and time: " + convertedDay + " " +endTimeEasternText + "\n";

            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(invalidFields);
        alert.showAndWait();
    }

}

    /** Cancels the changes when the Cancel button is pressed and the user presses Yes to confirm.
     * If the user presses no, the action is cancelled. */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all changes. Would you like to continue?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            AppointmentDateTime.time.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            Parent addPartParent = loader.load();
            Scene scene = new Scene(addPartParent);
            stage.setScene(scene);
            stage.show();
        }

    }

    /** Adds or updates an existing appointment based on the appointment Id.
     * If the appointment Id isn't found, it stores it as a new appointment.
     * @param appointments Used for storing all the appointment information.
     */
    public void updateAppointment(Appointments appointments) {
        boolean found = false;
        for (Appointments existing : appointmentList) {
            if (existing.getAppointmentId() == appointments.getAppointmentId()) {
                found = true;
                existing.setTitle(appointments.getTitle());
                existing.setDescription(appointments.getDescription());
                existing.setLocation(appointments.getLocation());
                existing.setContact(appointments.getContact());
                existing.setType(appointments.getType());
                existing.setStartDate(appointments.getStartDate());
                existing.setEndDate(appointments.getEndDate());
                existing.setCustomerId(appointments.getCustomerId());
                existing.setUserId(appointments.getUserId());
            }
        }
        if (!found) {
            Lists.addAppointment(appointments);
        }
    }

    /** Sets the Appointment Id as visible when a new appointment is selected. */
    public void setData(Integer id) {
        AppointmentIdField.setText(String.valueOf(id));
    }

    /** Sets all the information in the appointment editor when the modify appointment is selected from the Schedule. */
    public void setData(Integer appointmentId, String title, String description, String location, String contact, String type, String startDate, String endDate, Integer customerId, Integer userId) {
        AppointmentIdField.setText(String.valueOf(appointmentId));
        titleField.setText(String.valueOf(title));
        descriptionField.setText(String.valueOf(title));
        locationField.setText(String.valueOf(location));
        contactsBox.setValue(contact);
        typeField.setText(String.valueOf(type));

        customerBox.setValue(customerId);
        userBox.setValue(userId);

        savedId = appointmentId;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeMerge = LocalDateTime.parse(startDate, formatter);
        endTimeMerge = LocalDateTime.parse(endDate, formatter);

        LocalDate stringToStartDate = dateTimeMerge.toLocalDate();
        LocalTime stringToStartTime = dateTimeMerge.toLocalTime();
        LocalDate stringToEndDate = endTimeMerge.toLocalDate();
        LocalTime stringToEndTime = endTimeMerge.toLocalTime();

        selectDate.setValue(stringToStartDate);
        startTime.setValue(stringToStartTime);
        selectEndDate.setValue(stringToEndDate);
        endTime.setValue(stringToEndTime);

    }
    /** Gets the date */
    public void getDate(ActionEvent actionEvent) {
        LocalDate myDate = selectDate.getValue();
    }
}
