package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Lists class used for storing some of the lists.
 * Stores the appointment, customer, and user list.
 */
public class Lists {

    /** Stores the appointments. */
    public static ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

    /** Returns the appointment list. */
    public static ObservableList<Appointments> getAppointments() {
        return appointmentList;
    }
    /** Adds an appointment to the list. */
    public static void addAppointment(Appointments appointment) {
        appointmentList.add(appointment);
    }

    /** Stores the customers. */
    public static ObservableList<Customers> customerList = FXCollections.observableArrayList();

    /** Returns the customer list. */
    public static ObservableList<Customers> getCustomerList() {
        return customerList;
    }
    /** Adds a customer to the list. */
    public static void addCustomer(Customers customer) {
        customerList.add(customer);
    }

    /** Stores the users. */
    public static ObservableList<Integer> users = FXCollections.observableArrayList();
    /** Adds a user to the list */
    public static void addUser(Integer user) {
        users.add(user);
    }
}


