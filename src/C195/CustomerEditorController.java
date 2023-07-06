package C195;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import static C195.Lists.appointmentList;
import static C195.Lists.customerList;

/** CustomerEditorController class used for the modification and addition of customers.
 * The text fields and comboBoxes are either empty or populated with data depending on an add or customer modification.
 */
public class CustomerEditorController implements Initializable {
    /** Customer ID text field. */
    public TextField customerIdField;
    /** Name text field. */
    public TextField nameField;
    /** Address text field. */
    public TextField addressField;
    /** Phone text field. */
    public TextField phoneField;
    /** Postal code text field. */
    public TextField zipField;
    /** Anchorpane for the page. */
    public AnchorPane mainWindow;
    /** State combobox. */
    public ComboBox stateBox;
    /** Country combobox. */
    public ComboBox countryBox;
    /** Used for storing ther cutomer Id. */
    public static int customerId;


    /** Sets the customer ID value. */
    public static void customerId(int num) {
        customerId = num;
    }
    /** Returns the customer ID. */
    public static int getCustomerId() {
        return customerId;
    }

    /** Initializes all the comboboxes and has the lambda expressions for each of the combo boxes.
     * The Country box has a lambda expression used for detecting when a Country is selected, which then
     * populates the States or Provinces for the Country.
     * */
    public void initialize(URL url, ResourceBundle resourceBundle){
        stateBox.setItems(States.states);
        countryBox.setItems(Country.countries);

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

    /** Saves the customer information.
     * If there is missing information, exceptions are thrown and handled to notify the user with detailed information for what's missing.
     */
    public void SaveButton(ActionEvent actionEvent) throws IOException {
        try {
            Integer customerId = Integer.valueOf(customerIdField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String zipcode = zipField.getText();
            String phone = phoneField.getText();
            String state = String.valueOf(stateBox.getValue());
            String country = String.valueOf(countryBox.getValue());

            if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || zipField.getText().isEmpty()
                    || phoneField.getText().isEmpty() || stateBox.getSelectionModel().isEmpty()
                    || (countryBox.getSelectionModel() == null))
            {
                throw new NumberFormatException();
            }

            Customers customer = new Customers(customerId, name, address, zipcode, phone, state, country);
            updateCustomer(customer);

            UpdateDatabase.addCustomer(customerId, name, address,zipcode, phone);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            Parent addPartParent = loader.load();
            Scene scene = new Scene(addPartParent);
            stage.setScene(scene);
            stage.show();
        }

            catch (NumberFormatException | IOException | SQLException e) {
                String invalidFields = "The following fields have errors:\n";
                if (nameField.getText().isEmpty()) {
                    invalidFields += "Name is empty\n";
                }
                if (phoneField.getText().isEmpty()) {
                    invalidFields += "Phone is empty\n";
                }
                if (addressField.getText().isEmpty()) {
                    invalidFields += "Address is empty\n";
                }
                if (zipField.getText().isEmpty()) {
                    invalidFields += "Postal code is empty\n";
                }
                if (countryBox.getSelectionModel().isEmpty()) {
                    invalidFields += "Country is empty\n";
                }
                if (stateBox.getSelectionModel().isEmpty()) {
                    invalidFields += "State is empty\n";
                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(invalidFields);
                alert.showAndWait();
        }
    }

    /** Cancels all changes when the cancel button is clicked.
     * The user is asked if they would like to cancel changes, and if the user presses no, then the user will remain on the editor.
     * If the user says yes to cancelling the changes, either the new customer won't be added, or the customer won't be updated.
     * @param actionEvent
     * @throws IOException
     */
    public void CancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel all changes. Would you like to continue?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
            Stage stage = (Stage) mainWindow.getScene().getWindow();
            stage.close();
            Parent addPartParent = loader.load();
            Scene scene = new Scene(addPartParent);
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Adds or updates a customer depending if the customer already exists.
     * If the customer exists, information is updated.
     * If the customer doesn't exist, a new customer is added.
     */
    public void updateCustomer(Customers customers) {
        boolean found = false;
        for (Customers existing : customerList) {
            if (existing.getCustomerId() == customers.getCustomerId()) {
                found = true;
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

    /** Sets the customer ID field only when adding a new customer.
     * The field is disabled so it allows the user to see the customer ID before adding a new customer.
     */
    public void setData(Integer id) {
        customerIdField.setText(String.valueOf(id));

    }

    /** Sets all the fields and boxes when a customer is updated.
     * @param customerId Sets the customer Id
     * @param name Sets the customer name
     * @param phone Sets the customer phone number
     * @param address Sets the customer address
     * @param zip Sets the customer postal code
     * @param state Sets the customer State
     * @param country Sets the customer Country
     */
    public void setData(Integer customerId, String name, String phone, String address, String zip, String state, String country) {
        customerIdField.setText(String.valueOf(customerId));
        nameField.setText(String.valueOf(name));
        phoneField.setText(String.valueOf(phone));
        addressField.setText(String.valueOf(address));
        zipField.setText(String.valueOf(zip));
        countryBox.setValue(country);
        stateBox.setValue(state);


    }
}

