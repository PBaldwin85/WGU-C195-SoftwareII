package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static C195.Lists.appointmentList;


public class Reports implements Initializable {

    @FXML
    private TableView contactsTableView;

    @FXML
    private ComboBox contactsBox;
    @FXML
    private ComboBox monthBox;
    @FXML
    private ComboBox typeBox;

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






    public void initialize(URL url, ResourceBundle resourceBundle) {
        Type.populateTypes();
        Months.populateMonths();
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
            System.out.println("Number of selected type: " + count);
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
                    System.out.println(existing.getAppointmentId());
                    System.out.println(existing.getTitle());

                    Integer id = existing.getAppointmentId();
                    String title = existing.getTitle();
                    String type = existing.getType();
                    String description = existing.getDescription();
                    String startDate = existing.getStartDate();
                    String endDate = existing.getEndDate();
                    Integer customerId = existing.getCustomerId();

                    FilteredAppointments filteredAppointments = new FilteredAppointments(id, title, type, description, startDate, endDate, customerId);
                    FilteredAppointments.filteredAppointmentList.add(filteredAppointments);

                    System.out.println(FilteredAppointments.filteredAppointmentList);

                    contactsTableView.setItems(FilteredAppointments.getAppointments());
                    appointmentId.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
                    appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
                    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
                    startColumn.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
                    endColumn.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
                    customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));



                }
            }

        });




    }


}
