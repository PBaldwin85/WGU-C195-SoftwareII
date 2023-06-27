package C195;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    public AnchorPane mainWindow;
    public TableView appointmentTableView;
    public TableColumn appointmentId;
    public TableColumn appointmentTitle;
    public TableColumn appointmentDescription;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerColumn;
    public TableColumn userColumn;
    public TableColumn customerIdColumn;
    public TableColumn nameColumn;
    public TableColumn addressColumn;
    public TableColumn postalCodeColumn;
    public TableColumn phoneColumn;
    public TableView customerTableView;
    public TableColumn stateColumn;
    public TableColumn countryColumn;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTableView.setItems(Lists.getAppointments());
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        customerTableView.setItems(Lists.getCustomerList());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("Zip"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("State"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));

    }

    public void AddButton(ActionEvent actionEvent) throws IOException {
        AppointmentEditorController.AppointmentId(Main.generateAppointmentId());




        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentEditor.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        AppointmentEditorController AppointmentEditorController = loader.getController();
        AppointmentEditorController.setData(Main.getAppointmentId());
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    public void AddCustomer(ActionEvent actionEvent) throws IOException {
        CustomerEditorController.customerId(Main.generateCustomerId());




        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerEditor.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        CustomerEditorController CustomerEditorController = loader.getController();
        CustomerEditorController.setData(Main.getCustomerId());
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    public void UpdateAppointment(ActionEvent actionEvent) {
        Appointments selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentEditor.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            try {
                Parent addPartParent = loader.load();
                AppointmentEditorController AppointmentEditorController = loader.getController();
                AppointmentEditorController.setData(
                        selectedAppointment.getAppointmentId(),
                        selectedAppointment.getTitle(),
                        selectedAppointment.getDescription(),
                        selectedAppointment.getLocation(),
                        selectedAppointment.getContact(),
                        selectedAppointment.getType(),
                        selectedAppointment.getStartDate(),
                        selectedAppointment.getEndDate(),
                        selectedAppointment.getCustomerId(),
                        selectedAppointment.getUserId()
                );
                Scene scene = new Scene(addPartParent);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }



    public void DeleteAppointment(ActionEvent actionEvent) {
        Appointments selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Would you like to delete the selected part?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                Appointments.deleteAppointment(selectedAppointment);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to delete!");
            alert.showAndWait();
        }
    }


    public void DeleteCustomer(ActionEvent actionEvent) {
    }



    public void UpdateCustomer(ActionEvent actionEvent) {
    }


}
