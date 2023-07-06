package C195;

import helper.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UpdateDatabase {
    public static void addCustomer(Integer customerId, String name, String address, String zipcode, String phone) throws SQLException {
        try {

            String query = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Phone, Address, Postal_Code) " +
                    "VALUES (customerId, name, phone, address, zipcode);";

            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return;
    }

}
