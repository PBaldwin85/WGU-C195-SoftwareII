package C195;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public void start(Stage stage) throws IOException {
        JDBC.openConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 875, 325);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
        JDBC.closeConnection();
    }
    /**
    public static void main(String[] args) {
        JDBC.openConnection();




        JDBC.closeConnection();
    }
     */




}
