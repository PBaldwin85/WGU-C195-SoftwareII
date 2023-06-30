package C195;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static C195.Lists.appointmentList;
import static C195.Lists.customerList;

public class AppointmentEditorController implements Initializable {
    public AnchorPane mainWindow;
    public TextField AppointmentIdField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public TextField startDateField;
    public TextField EndDateField;
    public TextField CustomerIdField;
    public TextField userField;
    public ComboBox contactsBox;

    private ComboBox startDay;
    private ComboBox startMonth;
    private ComboBox startYear;
    @FXML
    private ComboBox startTime;
    @FXML
    private ComboBox endTime;

    Integer savedId;

    @FXML
    private DatePicker selectDate;
    @FXML
    private DatePicker selectEndDate;
    private Label myLabel;

    private LocalDateTime dateTimeMerge;
    private LocalDateTime endTimeMerge;


    /** Holds the part ID value. */
    public static int appointment;
    /** Sets the part ID value. */
    public static void AppointmentId(int num) {
        appointment = num;
    }
    /** Returns the part ID. */
    public static int getAppointmentId() {
        return appointment;
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsBox.setItems(Contacts.contacts);
        contactsBox.getSelectionModel().selectFirst();


        /**
        AppointmentDateTime.populateTime();
         */

        selectDate.setOnAction(event-> {
            AppointmentDateTime.time.clear();
            AppointmentDateTime.endTimeList.clear();

            LocalDate selectedDate = selectDate.getValue();
            selectEndDate.setValue(selectedDate);
            AppointmentDateTime.populateTime();

            for (Appointments existing : appointmentList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
                LocalDateTime endDate = LocalDateTime.parse(existing.getEndDate(), formatter);
                LocalDate stringToStartDate = startDate.toLocalDate();
                LocalTime stringToStartTime = startDate.toLocalTime();
                LocalDate stringToEndDate = endDate.toLocalDate();
                LocalTime stringToEndTime = endDate.toLocalTime();

                if (selectDate.getValue().isEqual(stringToStartDate)) {
                    AppointmentDateTime.removeMatches(stringToStartTime, stringToEndTime);
                }
            }


            /** Works to set time list
            startTime.setItems(AppointmentDateTime.time);
            endTime.setItems(AppointmentDateTime.time);
            */
        });


        startTime.setItems(AppointmentDateTime.time);

        startTime.setOnAction(event -> {
            boolean appointmentsAfter = false;
            LocalDate selectedDate = selectDate.getValue();
            LocalTime selectedTime = LocalTime.parse((CharSequence) startTime.getValue());
            dateTimeMerge = LocalDateTime.of(selectedDate, selectedTime);

            for (Appointments existing : appointmentList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
                LocalDateTime endDate = LocalDateTime.parse(existing.getEndDate(), formatter);
                LocalDate stringToStartDate = startDate.toLocalDate();
                LocalTime stringToStartTime = startDate.toLocalTime();
                LocalDate stringToEndDate = endDate.toLocalDate();
                LocalTime stringToEndTime = endDate.toLocalTime();

                System.out.println("Selected start time: " + selectedTime);
                System.out.println("stringToStartTime: " + stringToStartTime);

                if (stringToStartTime.isAfter(selectedTime)) {
                    appointmentsAfter = true;
                }

                if (selectDate.getValue().isEqual(stringToStartDate) && selectedTime.isBefore(LocalTime.from(stringToStartTime))) {
                    LocalTime test = stringToStartTime;

                    AppointmentDateTime.setEndTimes(selectedTime, test);
                    endTime.setItems(AppointmentDateTime.endTimeList);

                }
            }
            if (appointmentsAfter == false) {
                AppointmentDateTime.setEndTimes(selectedTime, LocalTime.parse("22:01:00"));
                endTime.setItems(AppointmentDateTime.endTimeList);

            }

        });

        endTime.setOnAction(event -> {
            LocalDate selectedDate = selectDate.getValue();
            LocalTime selectedTime = LocalTime.parse((CharSequence) endTime.getValue());
            endTimeMerge = LocalDateTime.of(selectedDate, selectedTime);
        });

    }

    public void saveButton(ActionEvent actionEvent) throws IOException {
        Integer appointmentId = Integer.valueOf(AppointmentIdField.getText());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = String.valueOf(contactsBox.getValue());
        String type = typeField.getText();
        Integer customerId = Integer.valueOf(CustomerIdField.getText());
        Integer userId = Integer.valueOf(userField.getText());


        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
        String formattedStartDateTime = dateTimeMerge.format(test);
        String formattedEndDateTime = endTimeMerge.format(test);



        Appointments appointment = new Appointments(appointmentId, title, description, location, contact, type, formattedStartDateTime, formattedEndDateTime, customerId, userId);

        updateAppointment(appointment);



        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all changes. Would you like to continue?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            Parent addPartParent = loader.load();
            Scene scene = new Scene(addPartParent);
            stage.setScene(scene);
            stage.show();
        }

    }

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

    public void setData(Integer id) {
        AppointmentIdField.setText(String.valueOf(id));
    }

    public void setData(Integer appointmentId, String title, String description, String location, String contact, String type, String startDate, String endDate, Integer customerId, Integer userId) {
        AppointmentIdField.setText(String.valueOf(appointmentId));
        titleField.setText(String.valueOf(title));
        descriptionField.setText(String.valueOf(title));
        locationField.setText(String.valueOf(location));
        contactsBox.setValue(contact);
        typeField.setText(String.valueOf(type));

        CustomerIdField.setText(String.valueOf(customerId));
        userField.setText(String.valueOf(userId));
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

    public void getDate(ActionEvent actionEvent) {
        LocalDate myDate = selectDate.getValue();
        System.out.println(myDate.toString());
    }
}
