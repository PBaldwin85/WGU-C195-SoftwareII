package C195;

import helper.JDBC;

import java.sql.ResultSet;
import java.sql.Statement;

public class Appointments extends Lists {

    private Integer appointmentId;

    private String title;
    private String description;
    private String location;

    private String contact;

    private String type;

    private String startDate;

    private String endDate;

    private Integer customerId;

    private Integer userId;



    Appointments(Integer appointmentId, String title, String description, String location, String contact, String type, String startDate, String endDate, Integer customerId, Integer userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.userId = userId;
    }

    public static void deleteAppointment(Appointments selectedAppointment) {
        Lists.appointmentList.remove(selectedAppointment);
    }

    public Integer getAppointmentId() {return appointmentId;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public String getLocation() {return location;}

    public String getContact() {return contact;}

    public String getType() {return type;}

    public String getStartDate() {return startDate;}

    public String getEndDate() {return endDate;}

    public Integer getCustomerId() {return customerId;}

    public Integer getUserId() {return userId;}



    public static void populateAppointments() {
        try {
            String query = "SELECT * FROM client_schedule.appointments";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Main.generateAppointmentId();
                Integer id = Integer.valueOf(result.getString("Appointment_ID"));
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                String start = result.getString("Start");
                String end = result.getString("End");
                Integer customerId = Integer.valueOf(result.getString("Customer_ID"));
                Integer userId = Integer.valueOf(result.getString("User_ID"));

                Integer contactId = Integer.valueOf(result.getString("Contact_ID"));
                String contact = (String) Contacts.getName(contactId);

                Appointments appointments = new Appointments(id, title, description, location, contact, type, start
                        , end, customerId, userId);
                Lists.appointmentList.add(appointments);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}