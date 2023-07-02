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


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static C195.Users.userList;

public class LoginController {

    public AnchorPane mainWindow;
    public Label setLocation;
    public TextField username;
    public TextField password;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button login;
    public Button exit;
    public Label locationLabel;


    public void login() throws IOException {

         Locale locale = new Locale("fr");
         ResourceBundle rb = ResourceBundle.getBundle("C195/Nat", locale);


        /**
        ResourceBundle rb = ResourceBundle.getBundle("C195/Nat", Locale.getDefault());
         */



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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(rb.getString("Error"));
            alert.setTitle(rb.getString("Error"));
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
                alert.setTitle(rb.getString("Error"));
                alert.setHeaderText(rb.getString("Error"));
                alert.setContentText(rb.getString("incorrectLogin"));
                alert.showAndWait();
                return;
            }
        }

    }
    public void exit(ActionEvent actionEvent) {
        Platform.exit();    }

    public void initialize() {
        Locale locale = new Locale("fr");
        ResourceBundle rb = ResourceBundle.getBundle("C195/Nat", locale);

        ZoneId zoneId = ZoneId.systemDefault();
        String location = zoneId.getId();
        setLocation.setText(location);
        Main.loggedIn = false;
        Users.getUsers();

        if (Locale.getDefault().equals("fr")) {
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            locationLabel.setText("Emplacement");
            login.setText("Connexion");
            exit.setText("Se déconnecter");
        }


    }




}
