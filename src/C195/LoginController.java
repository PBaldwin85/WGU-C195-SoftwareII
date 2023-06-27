package C195;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;

public class LoginController {

    public AnchorPane mainWindow;
    public Label setLocation;

    public void login() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scheduler.fxml"));
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.close();
        Parent addPartParent = loader.load();
        Scene scene = new Scene(addPartParent);
        stage.setScene(scene);
        stage.show();
    }
    public void exit(ActionEvent actionEvent) {
        Platform.exit();    }

    public void initialize() {
        ZoneId zoneId = ZoneId.systemDefault();
        String location = zoneId.getId();
        setLocation.setText(location);
    }
}
