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
import java.sql.SQLException;
import java.util.ResourceBundle;

import static C195.Lists.appointmentList;
import static C195.Lists.customerList;

public class CustomerEditorController implements Initializable {
    public TextField customerIdField;
    public TextField nameField;
    public TextField addressField;
    public TextField phoneField;
    public TextField zipField;
    public AnchorPane mainWindow;
    public ComboBox stateBox;
    public ComboBox countryBox;
    public static int customerId;


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
        countryBox.setItems(Country.countries);

        countryBox.getSelectionModel().selectFirst();

        countryBox.setOnAction(event -> {
            String selectedCountry = (String) countryBox.getValue();
            if (selectedCountry.equals("U.S")) {
                States.states.clear();
                try {
                    States.getStates(1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                stateBox.setItems(States.states);
                stateBox.getSelectionModel().selectFirst();
            }
            if (selectedCountry.equals("UK")) {
                States.states.clear();
                try {
                    States.getStates(2);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                stateBox.setItems(States.states);
                stateBox.getSelectionModel().selectFirst();
            }
            if (selectedCountry.equals("Canada")) {
                States.states.clear();
                try {
                    States.getStates(3);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                stateBox.setItems(States.states);
                stateBox.getSelectionModel().selectFirst();
            }
        });

    }




    public void SaveButton(ActionEvent actionEvent) throws IOException {
        Integer customerId = Integer.valueOf(customerIdField.getText());
        String name = nameField.getText();
        String address = addressField.getText();
        String zipcode = zipField.getText();
        String phone = phoneField.getText();
        String state = String.valueOf(stateBox.getValue());
        String country = String.valueOf(countryBox.getValue());



        Customers customer = new Customers(customerId, name, address, zipcode, phone, state, country);

        updateCustomer(customer);

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

    public void updateCustomer(Customers customers) {
        boolean found = false;
        for (Customers existing : customerList) {
            if (existing.getCustomerId() == customers.getCustomerId()) {
                found = true;
                System.out.println("match");
                existing.setName(customers.getName());
                existing.setPhone(customers.getPhone());
                existing.setAddress(customers.getAddress());
                existing.setZip(customers.getZip());
                existing.setState(customers.getState());
                existing.setCountry(customers.getCountry());
            }
        }
        if (!found) {
            Lists.addCustomer(customers);
        }
    }

    public void setData(Integer id) {

        customerIdField.setText(String.valueOf(id));

    }

    public void setData(Integer customerId, String name, String phone, String address, String zip, String state, String country) {
        customerIdField.setText(String.valueOf(customerId));
        nameField.setText(String.valueOf(name));
        phoneField.setText(String.valueOf(phone));
        addressField.setText(String.valueOf(address));
        zipField.setText(String.valueOf(zip));
        stateBox.setValue(state);
        countryBox.setValue(country);
    }
}

