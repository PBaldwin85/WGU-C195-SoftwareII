package C195;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main extends Application {



    public void start(Stage stage) throws IOException, SQLException {
        JDBC.openConnection();

        Country.populateCountries();
        Customers.populateCustomers();
        Appointments.populateAppointments();
        Contacts.populateNames();

        /**
        Appointments appointment = new Appointments(3, "t", "test", "test", "Test",
                "Test", "2023-06-30 06:00:00", "2023-06-30 09:00:00", 1, 1);

        Appointments appointment2 = new Appointments(4, "t", "test", "test", "Test",
                "Test", "2023-06-30 11:00:00", "2023-06-30 12:00:00", 1, 1);
        Appointments appointment3 = new Appointments(5, "t", "test", "test", "Test",
                "Test", "2023-06-30 13:00:00", "2023-06-30 15:00:00", 1, 1);

        Appointments.addAppointment(appointment);

        Appointments.addAppointment(appointment2);
        Appointments.addAppointment(appointment3);
         */

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    private static int appointmentId = 0;

    public static int generateAppointmentId() {
        appointmentId ++;
        return appointmentId;
    }

    public static int getAppointmentId() {
        return appointmentId;
    }


    private static int customerId = 0;

    public static int generateCustomerId() {
        customerId ++;
        return customerId;
    }

    public static int getCustomerId() {
        return customerId;
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
