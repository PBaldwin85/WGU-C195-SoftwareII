package C195;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentEditorController {
    public AnchorPane mainWindow;
    public TextField AppointmentIdField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField contactField;
    public TextField typeField;
    public TextField startDateField;
    public TextField EndDateField;
    public TextField CustomerIdField;
    public TextField userField;

    public void saveButton(ActionEvent actionEvent) throws IOException {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String type = typeField.getText();
        String startDate = startDateField.getText();
        String endDate = EndDateField.getText();
        String customerId = CustomerIdField.getText();
        String userId = userField.getText();

        Appointments appointment = new Appointments(title, description, location, contact, type, startDate, endDate, customerId, userId);

        AppointmentList.addAppointment(appointment);



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
}
