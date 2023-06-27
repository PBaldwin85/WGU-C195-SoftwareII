package C195;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static C195.Lists.appointmentList;

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

    Integer savedId;


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


    public void initialize(URL url, ResourceBundle resourceBundle){
        contactsBox.setItems(Contacts.contacts);

        contactsBox.getSelectionModel().selectFirst();

    }

    public void saveButton(ActionEvent actionEvent) throws IOException {
        Integer appointmentId = Integer.valueOf(AppointmentIdField.getText());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = String.valueOf(contactsBox.getValue());
        String type = typeField.getText();
        String startDate = startDateField.getText();
        String endDate = EndDateField.getText();
        Integer customerId = Integer.valueOf(CustomerIdField.getText());
        Integer userId = Integer.valueOf(userField.getText());


        Appointments appointment = new Appointments(appointmentId, title, description, location, contact, type, startDate, endDate, customerId, userId);

        updateAppointment(appointment);
        System.out.println("Saved id: " + savedId);
        System.out.println("Appointment ID: " + appointmentId);



        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();

    }

    public void updateAppointment(Appointments appointments) {
        boolean found = false;
        for (Appointments existing : appointmentList) {
            if (existing.getAppointmentId() == appointments.getAppointmentId()) {
                found = true;
                System.out.println("match");
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
        startDateField.setText(String.valueOf(startDate));
        EndDateField.setText(String.valueOf(endDate));
        CustomerIdField.setText(String.valueOf(customerId));
        userField.setText(String.valueOf(userId));

        savedId = appointmentId;
    }
}
