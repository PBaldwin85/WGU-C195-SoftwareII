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

public class CustomerEditorController implements Initializable {
    public TextField customerIdField;
    public TextField nameField;
    public TextField addressField;
    public TextField phoneField;
    public TextField zipField;

    public static int customerId;
    public AnchorPane mainWindow;
    public ComboBox stateBox;
    public ComboBox countryBox;


    /** Sets the part ID value. */
    public static void customerId(int num) {
        customerId = num;
    }
    /** Returns the part ID. */
    public static int getCustomerId() {
        return customerId;
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        stateBox.setItems(States.states);
        countryBox.setItems(States.states);

        stateBox.getSelectionModel().selectFirst();
        countryBox.getSelectionModel().selectFirst();



    }


    public void SaveButton(ActionEvent actionEvent) throws IOException {
        Integer customerId = Integer.valueOf(customerIdField.getText());
        String name = nameField.getText();
        String address = addressField.getText();
        Integer zipcode = Integer.valueOf(zipField.getText());
        Integer phone = Integer.valueOf(phoneField.getText());

        String state = String.valueOf(stateBox.getValue());
        String country = String.valueOf(countryBox.getValue());



        Customers customers = new Customers(customerId, name, address, zipcode, phone, state, country);

        Lists.addCustomer(customers);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }

    public void CancelButton(ActionEvent actionEvent) {
    }

    public void setData(Integer id) {

        customerIdField.setText(String.valueOf(id));

    }
}

