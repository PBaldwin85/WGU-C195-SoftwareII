package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private TableView ContactsTableView;

    @FXML
    private ComboBox contactsBox;
    @FXML
    private ComboBox monthBox;
    @FXML
    private ComboBox typeBox;

    @FXML
    public Label typeOutput;




    public void initialize(URL url, ResourceBundle resourceBundle) {
        Type.populateTypes();
        typeBox.setItems(Type.typeList);

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




    }


}
