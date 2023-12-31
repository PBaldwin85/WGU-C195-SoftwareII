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
import static C195.Type.typeList;

/** Holds 2 different Lambda expressions.
 * Reports class used for managing the information on the reports page.
 */
public class Reports implements Initializable {
    /** Anchorpane window for the page */
    public AnchorPane mainWindow;
    /** Table view for filtered the contacts appointments. */
    @FXML
    private TableView contactsTableView;
    @FXML
    private TableView monthTableView;
    /** Country combobox used to select a Country to see how many customers there are in the selected Country. */
    @FXML
    private ComboBox countryBox;
    /** Contacts combobox for selecting a contact to filter the appointment list to. */
    @FXML
    private ComboBox contactsBox;
    /** Month combobox for selecting a month to see the amount of appointments in a month. */
    @FXML
    private ComboBox monthBox;
    /** Type combobx used for selecting a type to see the amount of appointments per type. */
    @FXML
    private ComboBox typeBox;
    /** The Country output label used for printing out the number of customers in a Country. */
    @FXML
    public Label countryOutput;
    /** The type output label used for printing out the number of appointments for the selected type. */
    @FXML
    public Label typeOutput;
    /** The Month output label used for printing out the number of appointments for the selected Month. */
    public Label monthOutput;
    /** Appointment ID table column. */
    @FXML
    public TableColumn appointmentId;
    /** Title column */
    @FXML
    public TableColumn appointmentTitle;
    /** Type column. */
    public TableColumn typeColumn;
    /** Description column. */
    public TableColumn descriptionColumn;
    /** Start date and time column. */
    public TableColumn startColumn;
    /** End date and time column. */
    public TableColumn endColumn;
    /** Customer ID column. */
    public TableColumn customerIdColumn;
    public TableColumn monthColumn;
    public TableColumn TypeColumnInMonths;
    public TableColumn appointmentsColumn;
    /** Back button to go back to the appointment and customer view. */
    @FXML
    private Button back;
    /** Logout button to go back to the login screen. */
    @FXML
    private Button logout;

    /** Initializes the reports page and holds lambda expressions for the comboboxes.
     * Populates and sets the comboboxes.
     * The Contact lambda on action expression is used for detecting when the user has made a selection.
     * Once a contact has been selected by the user, it uses a lambda stream expresssion to filter all the appointments for that contact.
     * The stream allows me to not use a for loop on the appointment list.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Type.populateTypes();
        Months.populateMonths();
        countryBox.setItems(Country.countries);
        monthBox.setItems(Months.monthList);
        contactsBox.setItems(Contacts.contacts);

        /** Lambda on action and stream for filtering the appointments for the selected contact. */
        contactsBox.setOnAction(event-> {
            FilteredAppointments.filteredAppointmentList.clear();

            appointmentList.stream()
                    .filter(existing -> contactsBox.getValue().equals(existing.getContact()))
                    .forEach(existing -> {
                                Integer id = existing.getAppointmentId();
                                String title = existing.getTitle();
                                String type = existing.getType();
                                String description = existing.getDescription();
                                String startDate = existing.getStartDate();
                                String endDate = existing.getEndDate();
                                Integer customerId = existing.getCustomerId();
                                FilteredAppointments filteredAppointments = new FilteredAppointments(id, title, type, description, startDate, endDate, customerId);
                                FilteredAppointments.addAppointment(filteredAppointments);
            });

            contactsTableView.setItems(FilteredAppointments.getAppointments());
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        });

        MonthReport.monthTypeList.clear();

        monthBox.setOnAction(event-> {
            MonthReport.monthTypeList.clear();
            for (String existingType : typeList) {
                int typeCount = 0;
                for (Appointments existing : appointmentList) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime startDate = LocalDateTime.parse(existing.getStartDate(), formatter);
                    Month month = startDate.getMonth();
                    String toUpper = (String) monthBox.getValue();
                    String selectedMonthToUpper = toUpper.toUpperCase();
                    if (existingType.equals(existing.getType()) && selectedMonthToUpper.equals(month.toString())) {
                        typeCount += 1;
                    }
                }
                MonthReport monthReport = new MonthReport((String) monthBox.getValue(), existingType, typeCount);
                MonthReport.addMonth(monthReport);
            }

            monthTableView.setItems(MonthReport.getMonths());
            monthColumn.setCellValueFactory(new PropertyValueFactory<>("Month"));
            TypeColumnInMonths.setCellValueFactory(new PropertyValueFactory<>("Type"));
            appointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("Appointments"));

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

    /** Action event from the user pressing the back button.
     * Goes back to the appointment schedule and customer screen.
     */
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    /** Action event from the user pressing the logout button.
     * Logs out and goes back to the login screen.
     */
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
