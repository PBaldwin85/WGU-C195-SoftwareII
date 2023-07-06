package C195;

import helper.JDBC;

import java.sql.*;
import java.time.LocalDateTime;


public class UpdateDatabase {
    public static void addCustomer(Integer customerId, String name, String address, String zipcode, String phone) throws SQLException {
        try (Connection connection = JDBC.getConnection();) {
            Boolean exists = false;
            String query = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?";
            PreparedStatement queryStatement = connection.prepareStatement(query);
            queryStatement.setInt(1, customerId);

            ResultSet result = queryStatement.executeQuery();
            while (result.next()) {
                Integer databaseCustomerId = Integer.valueOf(result.getString("Customer_ID"));
                System.out.println(databaseCustomerId);
                if (customerId == databaseCustomerId) {
                    String updateStatement = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
                    PreparedStatement statement = connection.prepareStatement(updateStatement);
                    statement.setString(1, name);
                    statement.setString(2, address);
                    statement.setString(3, zipcode);
                    statement.setString(4, phone);
                    statement.setString(5, String.valueOf(LocalDateTime.now()));
                    statement.setString(6, "test");
                    statement.setString(7, "1");
                    statement.setString(8, String.valueOf(customerId));

                    statement.executeUpdate();
                    exists = true;
                }
            }
            if (!exists) {
                String insertStatement = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(insertStatement);
                statement.setInt(1, customerId);
                statement.setString(2, name);
                statement.setString(3, address);
                statement.setString(4, zipcode);
                statement.setString(5, phone);
                statement.setString(6, String.valueOf(LocalDateTime.now()));
                statement.setString(7, "test");
                statement.setString(8, String.valueOf(LocalDateTime.now()));
                statement.setString(9, "test");
                statement.setString(10, "1");

                statement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return;
    }



}
