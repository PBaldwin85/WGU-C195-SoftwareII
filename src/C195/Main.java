package C195;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/** Main application class used for starting the application.
 * Calls functions which populate information from the database.
 */
public class Main extends Application {

    public void start(Stage stage) throws IOException, SQLException {
        JDBC.openConnection();

        Country.populateCountries();
        Customers.populateCustomers();
        Appointments.populateAppointments();
        Contacts.populateNames();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    /** Checks to see if the user has logged in. */
    public static boolean loggedIn = false;

    /** Initiates the appointment Id. */
    private static int appointmentId = 0;

    /** Generates an appointment Id. */
    public static int generateAppointmentId() {
        appointmentId ++;
        return appointmentId;
    }

    public static void setAppointmentId(Integer id) {
        appointmentId = id;
    }
    /** Gets an appointment Id. */
    public static int getAppointmentId() {
        return appointmentId;
    }

    /** Initiates the customer Id */
    private static int customerId = 0;
    /** Generates the custoemr Id */
    public static int generateCustomerId() {
        customerId ++;
        return customerId;
    }
    /** Gets the customer Id */
    public static int getCustomerId() {
        return customerId;
    }

    public static void setCustomerId(Integer id) {
        customerId = id;
    }
    /** Closes the database connection */
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
