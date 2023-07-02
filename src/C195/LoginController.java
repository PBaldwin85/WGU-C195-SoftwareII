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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;

import static C195.Users.userList;

public class LoginController {

    public AnchorPane mainWindow;
    public Label setLocation;
    public TextField username;
    public TextField password;


    public void login() throws IOException {
        try {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {

            String invalidFields = "The following fields have errors:\n";
            if (username.getText().isEmpty()) {
                invalidFields += "Username is empty\n";
            }
            if (password.getText().isEmpty()) {
                invalidFields += "Password is empty\n";
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(invalidFields);
            alert.showAndWait();
            return;
        }
        for (Users current : userList) {
            if (username.getText().equals(current.getUserName()) && (password.getText().equals(current.getPassword()))) {
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Incorrect username or password");
                alert.showAndWait();
                return;
            }
        }

    }
    public void exit(ActionEvent actionEvent) {
        Platform.exit();    }

    public void initialize() {
        ZoneId zoneId = ZoneId.systemDefault();
        String location = zoneId.getId();
        setLocation.setText(location);
        Main.loggedIn = false;
        Users.getUsers();


    }




}
