package C195;

import helper.JDBC;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class UpdateDatabase {
    public static void addCustomer(Integer customerId, String name, String address, String zipcode, String phone, String divisionId) throws SQLException {
        try {
            Boolean exists = false;
            Connection connection = JDBC.getConnection();
            String query = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?";
            PreparedStatement queryStatement = connection.prepareStatement(query);
            queryStatement.setInt(1, customerId);

            ResultSet result = queryStatement.executeQuery();
            while (result.next()) {
                Integer databaseCustomerId = Integer.valueOf(result.getString("Customer_ID"));
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
                    statement.setString(7, String.valueOf(divisionId));
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
                statement.setString(10, String.valueOf(divisionId));

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


    public static void addAppointment(Integer appointmentId, String title, String description, String location, String type, String startDate, String endDate, Integer customerId, Integer userId, Integer contactId) throws SQLException {
        try {
            Boolean exists = false;
            Connection connection = JDBC.getConnection();
            String query = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID = ?";
            PreparedStatement queryStatement = connection.prepareStatement(query);
            queryStatement.setInt(1, appointmentId);

            ResultSet result = queryStatement.executeQuery();
            while (result.next()) {
                Integer databaseAppointmentId = Integer.valueOf(result.getString("Appointment_ID"));
                if (appointmentId.equals(databaseAppointmentId)) {
                    String updateStatement = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
                    PreparedStatement statement = connection.prepareStatement(updateStatement);
                    String pattern = "yyyy-MM-dd HH:mm:ss";
                    DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
                    String timeStamp = DateTime.toUtc(ZoneId.systemDefault(),LocalDateTime.now()).format(test);

                    statement.setString(1, title);
                    statement.setString(2, description);
                    statement.setString(3, location);
                    statement.setString(4, type);
                    statement.setString(5, startDate);
                    statement.setString(6, endDate);
                    statement.setString(7, timeStamp);
                    statement.setString(8, "Script");
                    statement.setString(9, String.valueOf(customerId));
                    statement.setString(10, String.valueOf(userId));
                    statement.setString(11, String.valueOf(contactId));
                    statement.setString(12, String.valueOf(appointmentId));

                    statement.executeUpdate();
                    exists = true;
                }
            }
            if (!exists) {
                String insertStatement = "INSERT INTO client_schedule.appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(insertStatement);

                String pattern = "yyyy-MM-dd HH:mm:ss";
                DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
                String timeStamp = DateTime.toUtc(ZoneId.systemDefault(),LocalDateTime.now()).format(test);

                statement.setInt(1, appointmentId);
                statement.setString(2, title);
                statement.setString(3, description);
                statement.setString(4, location);
                statement.setString(5, type);
                statement.setString(6, startDate);
                statement.setString(7, endDate);
                statement.setString(8, timeStamp);
                statement.setString(9, "script");
                statement.setString(10, timeStamp);
                statement.setString(11, "script");
                statement.setString(12, String.valueOf(customerId));
                statement.setString(13, String.valueOf(userId));
                statement.setString(14, String.valueOf(contactId));

                statement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return;
    }

    public static void deleteAppointment(Integer appointmentId) throws SQLException {
        try {
            Connection connection = JDBC.getConnection();
            String deleteStatement = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
            PreparedStatement statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1, appointmentId);

            statement.executeUpdate();

        }
        catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
    }

}
