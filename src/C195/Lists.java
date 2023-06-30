package C195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Lists {

    public static ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

    /** Stores a product into a list. */

    public static ObservableList<Appointments> getAppointments() {
        return appointmentList;
    }

    public static void addAppointment(Appointments appointment) {
        appointmentList.add(appointment);
    }



    public static ObservableList<Customers> customerList = FXCollections.observableArrayList();

    /** Stores a product into a list. */

    public static ObservableList<Customers> getCustomerList() {
        return customerList;
    }

    public static void addCustomer(Customers customer) {
        customerList.add(customer);
    }

    public static ObservableList<Integer> users = FXCollections.observableArrayList();

    public static void addUser(Integer user) {
        users.add(user);
    }

}


