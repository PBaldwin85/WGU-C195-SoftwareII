package C195;

import helper.JDBC;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import static C195.Users.userList;

/** LoginController class used for the login screen.
 */
public class LoginController {

    /** Main window anchorpain. */
    public AnchorPane mainWindow;
    /** Username textfield. */
    public TextField username;
    /** Password textfield. */
    public TextField password;
    /** Username label to the left of the textfield. */
    public Label usernameLabel;
    /** Password label to the left of the textfield. */
    public Label passwordLabel;
    /** Login button. */
    public Button login;
    /** Exit button. */
    public Button exit;
    /** The location label used before the user location. */
    public Label locationLabel;
    /** Shows the users location on the bottom right of the screen. */
    public Label setLocation;

    /** Trigger when the user presses login.
     * Pulls the users language and translates the exceptions depending on if English or French is selected.
     * Checks the username and password against the database to verify login information.
     */
    public void login() throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("C195/Nat", Locale.getDefault());

        File file = new File("login_activity.text");
        FileWriter fw = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(fw);



        try {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            String invalidFields = rb.getString("fieldErrors");
            if (username.getText().isEmpty()) {
                invalidFields += rb.getString("userNameEmpty");
            }
            if (password.getText().isEmpty()) {
                invalidFields += rb.getString("passwordEmpty");
            }
            pw.println("Date: " + LocalDate.now() + "   Time: " + LocalTime.now() + "      Username:        " + username.getText() + "    Password:        " + password.getText() + "   Unsuccessful");
            pw.close();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(rb.getString("Error"));
            alert.setTitle(rb.getString("Error"));
            alert.setContentText(invalidFields);
            alert.showAndWait();
            return;
        }
        for (Users current : userList) {
            if (username.getText().equals(current.getUserName()) && (password.getText().equals(current.getPassword()))) {
                pw.println("Date: " + LocalDate.now() + "   Time: " + LocalTime.now() + "      Username: " + username.getText() + "       Password: " + password.getText() + "     Successful");
                pw.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
                Stage stage = (Stage) mainWindow.getScene().getWindow();
                stage.close();
                Parent addPartParent = loader.load();
                Scene scene = new Scene(addPartParent);
                stage.setScene(scene);
                stage.show();
                return;
            }
            else {
                pw.println("Date: " + LocalDate.now() + "   Time: " + LocalTime.now() + "      Username: " + username.getText() + "       Password: " + password.getText() + "         Unsuccessful");
                pw.close();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error"));
                alert.setHeaderText(rb.getString("Error"));
                alert.setContentText(rb.getString("incorrectLogin"));
                alert.showAndWait();
                return;
            }
        }



    }
    /** Exits the application */
    public void exit(ActionEvent actionEvent) {
        Platform.exit();    }

    /** Initializs the login screen.
     * Gets the users Zone ID and sets it on the screen.
     * Gets the users language and translates the the text to either English or French.
     */
    public void initialize() {
        ZoneId zoneId = ZoneId.systemDefault();
        String location = zoneId.getId();
        setLocation.setText(location);
        Main.loggedIn = false;
        Users.setUsers();

        ResourceBundle rb = ResourceBundle.getBundle("C195/Nat", Locale.getDefault());

        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        locationLabel.setText(rb.getString("Location"));
        login.setText(rb.getString("Login"));
        exit.setText(rb.getString("Exit"));


    }




}
