package C195;

import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppointmentDateTime {

    public void start(Stage stage) {
        DatePicker datePicker = new DatePicker();
        VBox root = new VBox(datePicker);
        Scene scene = new Scene(root, 300,200);

        stage.setTitle("Calender");
        stage.setScene(scene);
        stage.show();
    }
}
