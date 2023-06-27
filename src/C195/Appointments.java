package C195;

import helper.JDBC;

import java.sql.ResultSet;
import java.sql.Statement;

public class Appointments extends Lists {

    private Integer appointmentId;

    private final String title;
    private final String description;
    private final String location;

    private final String contact;

    private final String type;

    private final String startDate;

    private final String endDate;

    private final Integer customerId;

    private final Integer userId;



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

}