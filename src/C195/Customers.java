package C195;

import helper.JDBC;

import java.sql.ResultSet;
import java.sql.Statement;

public class Customers {

    private final Integer customerId;

    private final String name;
    private final String address;
    private final String zip;

    private final String phone;

    private final String state;

    private final String country;



    Customers(Integer id, String name, String address, String zip, String phone, String state, String country) {
        this.customerId = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.state = state;
        this.country = country;
    }


    public Integer getCustomerId() {return customerId;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public String getZip() {return zip;}

    public String getPhone() {return phone;}

    public String getState() {return state;}

    public String getCountry() {return country;}


    public static void populateCustomers() {
        try {
            String query = "SELECT * FROM client_schedule.customers;";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Integer id = Integer.valueOf(result.getString("Customer_ID"));
                String customerName = result.getString("Customer_Name");
                String phoneNumber = result.getString("Phone");
                String address = result.getString("Address");
                String zip = result.getString("Postal_Code");

                Customers customer = new Customers(id, customerName, address, zip, phoneNumber, "", "");
                Lists.customerList.add(customer);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
