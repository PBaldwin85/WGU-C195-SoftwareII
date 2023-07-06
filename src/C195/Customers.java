package C195;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

/** Customers class used for storing and accessing Customer information.
 */
public class Customers {
    /** Customer ID. */
    private final Integer customerId;
    /** Customer name. */
    private String name;
    /** Customer address. */
    private String address;
    /** Customer zip/postal code. */
    private String zip;
    /** Customer phone number. */
    private String phone;
    /** Customer State/Province. */
    private String state;
    /** Customer Country. */
    private String country;

    /** Sets all the customers information for storing.
     */
    Customers(Integer id, String name, String address, String zip, String phone, String state, String country) {
        this.customerId = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.state = state;
        this.country = country;
    }
    /** Deletes a customer */
    public static void deleteCustomer(Customers selectedCustomer) {
        Lists.customerList.remove(selectedCustomer);
    }
    /** Returns the customer observable list */
    public static ObservableList<Customers> getCustomers() {
        return Lists.customerList;
    }
    /** Returns the customer Id */
    public static ObservableList<Integer> getCustomerNames() {
        ObservableList<Integer> id = FXCollections.observableArrayList();
        for (Customers customer : Lists.customerList){
            id.add(customer.getCustomerId());
        }
        return id;
    }

    /** Gets the customer ID. */
    public Integer getCustomerId() {return customerId;}
    /** Gets the customer name. */
    public String getName() {return name;}
    /** Gets the customer address. */
    public String getAddress() {return address;}
    /** Gets the customer postal code. */
    public String getZip() {return zip;}
    /** Gets the customer phone number. */
    public String getPhone() {return phone;}
    /** Gets the customer State/Province. */
    public String getState() {return state;}
    /** Gets the customer Country. */
    public String getCountry() {return country;}

    /** Populates the Customer list from the database.
     * Connects to the database and then pulls from the correct columns and stores into the Customers observable list.
     */
    public static void populateCustomers() {
        try {
            String query = "SELECT * FROM client_schedule.customers";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Integer id = Integer.valueOf(result.getString("Customer_ID"));
                String customerName = result.getString("Customer_Name");
                String phoneNumber = result.getString("Phone");
                String address = result.getString("Address");
                String zip = result.getString("Postal_Code");
                Integer divisionId = Integer.valueOf(result.getString("Division_ID"));
                String division = (String) States.getDivision(divisionId);
                Integer countryId = (Integer) States.getCountryId(divisionId);
                String country = (String) Country.getCountry(countryId);
                Customers customer = new Customers(id, customerName, address, zip, phoneNumber, division, country);
                Lists.customerList.add(customer);
                Main.setCustomerId(id);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /** Sets the customer name. */
    public void setName(String name) {
        this.name = name;
    }
    /** Sets the customer phone. */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /** Sets the customer address. */
    public void setAddress(String address) {
        this.address = address;
    }
    /** Sets the customer postal code. */
    public void setZip(String zip) {
        this.zip = zip;
    }
    /** Sets the customer State. */
    public void setState(String state) {
        this.state = state;
    }
    /** Sets the customer Country. */
    public void setCountry(String country) {
        this.country = country;
    }
}
