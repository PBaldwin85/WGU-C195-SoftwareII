package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static C195.Lists.appointmentList;
import static C195.Lists.customerList;


public class Reports implements Initializable {
    public AnchorPane mainWindow;

    @FXML
    private TableView contactsTableView;
    @FXML
    private ComboBox countryBox;
    @FXML
    private ComboBox contactsBox;
    @FXML
    private ComboBox monthBox;
    @FXML
    private ComboBox typeBox;
    @FXML
    public Label countryOutput;
    @FXML
    public Label typeOutput;

    public Label monthOutput;
    @FXML
    public TableColumn appointmentId;
    @FXML
    public TableColumn appointmentTitle;
    public TableColumn typeColumn;
    public TableColumn descriptionColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIdColumn;
    @FXML
    private Button back;
    @FXML
    private Button logout;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Type.populateTypes();
        Months.populateMonths();
        countryBox.setItems(Country.countries);
        typeBox.setItems(Type.typeList);
        monthBox.setItems(Months.monthList);
        contactsBox.setItems(Contacts.contacts);


        typeBox.setOnAction(event-> {
            int count = 0;
            for (Appointments existing : appointmentList) {
                if (typeBox.getValue().equals(existing.getType())) {
                    count += 1;
                }
            }
            typeOutput.setText("Number of appointments: " + count);

        });

        monthBox.setOnAction(event-> {
            int count = 0;
            for (Appointments existing : appointmentList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
                Month month = startDate.getMonth();

                String toUpper = (String) monthBox.getValue();
                String selectedMonthToUpper = toUpper.toUpperCase();

                if (selectedMonthToUpper.equals(month.toString())) {
                    count += 1;
                }
            }
            monthOutput.setText("Number of appointments: " + count);

        });

        contactsBox.setOnAction(event-> {
            FilteredAppointments.filteredAppointmentList.clear();
            for (Appointments existing : appointmentList) {
                if (contactsBox.getValue().equals(existing.getContact())) {

                    Integer id = existing.getAppointmentId();
                    String title = existing.getTitle();
                    String type = existing.getType();
                    String description = existing.getDescription();
                    String startDate = existing.getStartDate();
                    String endDate = existing.getEndDate();
                    Integer customerId = existing.getCustomerId();

                    FilteredAppointments filteredAppointments = new FilteredAppointments(id, title, type, description, startDate, endDate, customerId);

                    if (!FilteredAppointments.filteredAppointmentList.contains(filteredAppointments)) {
                        System.out.println("Filtered appointment: " + filteredAppointments);
                        System.out.println("Filtered appointment list: " + FilteredAppointments.filteredAppointmentList);
                        FilteredAppointments.filteredAppointmentList.add(filteredAppointments);
                    }




                }
            }
            contactsTableView.setItems(FilteredAppointments.getAppointments());
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        });



        countryBox.setOnAction(event -> {
            int count = 0;
            for (Customers existing : customerList) {
                if (countryBox.getValue().equals(existing.getCountry())) {
                    count += 1;
                }
            }
            countryOutput.setText("Number of customers: " + count);
        });
    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
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


}
