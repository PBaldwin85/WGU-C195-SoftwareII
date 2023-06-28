package C195;

import helper.JDBC;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class Customers {

    private final Integer customerId;

    private String name;
    private String address;
    private String zip;

    private String phone;

    private String state;

    private String country;



    Customers(Integer id, String name, String address, String zip, String phone, String state, String country) {
        this.customerId = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.state = state;
        this.country = country;
    }

    public static void deleteCustomer(Customers selectedCustomer) {
        Lists.customerList.remove(selectedCustomer);
    }

    public static ObservableList<Customers> getCustomers() {
        return Lists.customerList;
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
            String query = "SELECT * FROM client_schedule.customers";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Main.generateCustomerId();
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
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
