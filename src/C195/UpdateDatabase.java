package C195;

import helper.JDBC;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class UpdateDatabase {
    public static void addCustomer(Integer customerId, String name, String address, String zipcode, String phone) throws SQLException {
        try {
            Boolean exists = false;
            Connection connection = JDBC.getConnection();
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
                    String pattern = "yyyy-MM-dd HH:mm:ss";
                    DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
                    String timeStamp = DateTime.toUtc(ZoneId.systemDefault(),LocalDateTime.now()).format(test);

                    statement.setString(1, name);
                    statement.setString(2, address);
                    statement.setString(3, zipcode);
                    statement.setString(4, phone);
                    statement.setString(5, timeStamp);
                    statement.setString(6, "script");
                    statement.setString(7, "1");
                    statement.setString(8, String.valueOf(customerId));

                    statement.executeUpdate();
                    exists = true;
                }
            }
            if (!exists) {
                String insertStatement = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(insertStatement);

                String pattern = "yyyy-MM-dd HH:mm:ss";
                DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
                String timeStamp = DateTime.toUtc(ZoneId.systemDefault(),LocalDateTime.now()).format(test);

                statement.setInt(1, customerId);
                statement.setString(2, name);
                statement.setString(3, address);
                statement.setString(4, zipcode);
                statement.setString(5, phone);
                statement.setString(6, timeStamp);
                statement.setString(7, "script");
                statement.setString(8, timeStamp);
                statement.setString(9, "script");
                statement.setString(10, "1");

                statement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return;
    }


    public static void deleteCustomer(Integer customerId) throws SQLException {
        try {
            Connection connection = JDBC.getConnection();
            String deleteStatement = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
            PreparedStatement statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1, customerId);

            statement.executeUpdate();

        }
        catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
    }
}
