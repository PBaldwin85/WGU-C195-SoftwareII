package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import static C195.Lists.appointmentList;

/** ScheduleController class used for controlling the Schedule page.
 * Holds the appointment and customer table.
 */
public class ScheduleController implements Initializable {
    /** The anchorpage for the schedule page. */
    public AnchorPane mainWindow;
    /** The appointments table. */
    public TableView appointmentTableView;
    /** The appointment ID column. */
    public TableColumn appointmentId;
    /** The appointment title column. */
    public TableColumn appointmentTitle;
    /** The appointment description column. */
    public TableColumn appointmentDescription;
    /** The appointment location column. */
    public TableColumn locationColumn;
    /** The appointment contact column. */
    public TableColumn contactColumn;
    /** The appointment type column. */
    public TableColumn typeColumn;
    /** The appointment start date and time column. */
    public TableColumn startColumn;
    /** The appointment end date and time column. */
    public TableColumn endColumn;
    /** The appointment customer column. */
    public TableColumn customerColumn;
    /** The appointment user ID column. */
    public TableColumn userColumn;
    /** The customer ID column. */
    public TableColumn customerIdColumn;
    /** The customer name column. */
    public TableColumn nameColumn;
    /** The customer address column. */
    public TableColumn addressColumn;
    /** The customer postal code column. */
    public TableColumn postalCodeColumn;
    /** The customer phone number column. */
    public TableColumn phoneColumn;
    /** The customer table. */
    public TableView customerTableView;
    /** The customer State column. */
    public TableColumn stateColumn;
    /** The customer Country column. */
    public TableColumn countryColumn;

    /** Initializes the Schedule page by setting the appointment and customer tables.
     * Checks to see if any appointments are within 15 minutes and notifies the user.
     */
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

        if (LoginController.loggedIn == false) {
            LoginController.loggedIn = true;
            boolean upcomingAppointments = false;
            for (Appointments existing : appointmentList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
                LocalDate stringToStartDate = startDate.toLocalDate();
                LocalTime stringToStartTime = startDate.toLocalTime();

                LocalTime currentTime = LocalTime.now();
                LocalTime adjusted = currentTime.plusMinutes(15);
                LocalDate currentDate = LocalDate.now();


                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime currentDateTimeEnd = currentDateTime.plusMinutes(15);

                System.out.println("startDate:" + startDate);
                System.out.println("current date/time:" + currentDateTime);
                System.out.println("current date/time plus 15:" + currentDateTimeEnd);

                if (startDate.isAfter(currentDateTime) && startDate.isBefore(currentDateTimeEnd)) {
                    upcomingAppointments = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appontment Information");
                    alert.setHeaderText("Appontment Information");
                    String appointmentComingUp = "There is an appointment scheduled in the next 15 minutes:\n";
                    appointmentComingUp += "Appointment Id: " + existing.getAppointmentId() + "\n";
                    appointmentComingUp += "Date: " + stringToStartDate + "\n";
                    appointmentComingUp += "Time: " + stringToStartTime + "\n";
                    alert.setContentText(appointmentComingUp);
                    alert.showAndWait();
                }
            }
            if (upcomingAppointments == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appontment Information");
                alert.setHeaderText("Appontment Information");
                alert.setContentText("There are no appointments coming up in the next 15 minutes.");
                alert.showAndWait();
            }
        }

    }
    /** Adds a new appointment when add is clicked.
     * Goes to the appointment editor page. */
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

    /** Adds a new customer when the add button is clicked.
     * Goes to the customer editor page.
     */
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

    /** Updates an appointment when the update button is clicked and sets all the proper information on the appointment page.
     * If an appointment isn't selected then a warning is displayed.
     */
    public void UpdateAppointment(ActionEvent actionEvent) {
        Appointments selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please select an appointment to update.");
            alert.showAndWait();
        }

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

    public void reports(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reports.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    /** Deletes an appointment. */
    public void DeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointments selectedAppointment = (Appointments) appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Would you like to delete the selected appointment?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                Appointments.deleteAppointment(selectedAppointment);
                UpdateDatabase.deleteAppointment(selectedAppointment.getAppointmentId());
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                String confirmationMessage = "Appointment deleted!\n\n";
                confirmationMessage += "Appointment number: " + selectedAppointment.getAppointmentId() + "\n";
                confirmationMessage += "Appointment type: " + selectedAppointment.getType() + "\n";
                alert.setContentText(confirmationMessage);
                ButtonType okButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();

            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        }
    }

    /** Deletes a customer. */
    public void DeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customers selectedCustomer = (Customers) customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
        ObservableList<Appointments> appointmentList = Appointments.getAppointments();
        for (Appointments appointments : appointmentList) {
            if (appointments.getCustomerId() == selectedCustomer.getCustomerId()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("The selected customer has an appointment associated with it and can't be deleted.");
                ButtonType okButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(okButton);
                Optional<ButtonType> result = alert.showAndWait();
                return;
            }
        }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Would you like to delete the selected customer?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                Customers.deleteCustomer(selectedCustomer);
                UpdateDatabase.deleteCustomer(selectedCustomer.getCustomerId());
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Customer deleted!");
                ButtonType okButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }
    }

    /** Updates a customer when the update button is clicked and sets all the proper information on the customer page.
     * If a customer isn't selected then a warning is displayed.
     */
    public void UpdateCustomer(ActionEvent actionEvent) {
        Customers selectedCustomer = (Customers) customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("Please select a customer to update.");
            alert.showAndWait();
        }

        if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerEditor.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            try {
                Parent addPartParent = loader.load();
                CustomerEditorController CustomerEditorController = loader.getController();
                CustomerEditorController.setData(
                        selectedCustomer.getCustomerId(),
                        selectedCustomer.getName(),
                        selectedCustomer.getPhone(),
                        selectedCustomer.getAddress(),
                        selectedCustomer.getZip(),
                        selectedCustomer.getState(),
                        selectedCustomer.getCountry()
                );
                Scene scene = new Scene(addPartParent);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /** Current week button.
     * Filters the appointments table to only show the current weeks appointments.
     */
    public void currentWeek(ActionEvent actionEvent) {
        LocalDateTime timeToCheck = LocalDateTime.now();
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        for (Appointments existing : appointmentList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
            LocalDate stringToStartDate = startDate.toLocalDate();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = timeToCheck.get(weekFields.weekOfWeekBasedYear());
            int appointmentsWeek = startDate.get(weekFields.weekOfWeekBasedYear());

            if ((currentWeek == appointmentsWeek) && timeToCheck.getYear() == startDate.getYear()) {
                appointments.add(existing);
            }
            appointmentTableView.setItems(appointments);
        }
    }

    /** Current Month button.
     * Filters the appointments table to only show the current Month appointments.
     */
    public void currentMonth(ActionEvent actionEvent) {
        LocalDateTime timeToCheck = LocalDateTime.now();
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        for (Appointments existing : appointmentList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
            LocalDate stringToStartDate = startDate.toLocalDate();

            if ((startDate.getMonth() == timeToCheck.getMonth()) && startDate.getYear() == timeToCheck.getYear()) {
                appointments.add(existing);
            }
            appointmentTableView.setItems(appointments);
        }
    }

    /** Display all button.
     * Shows all the appointments with no filter.
     */
    public void displayAll(ActionEvent actionEvent) {
        appointmentTableView.setItems(Lists.getAppointments());
    }
}
