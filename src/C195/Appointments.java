package C195;

import helper.JDBC;

import java.sql.ResultSet;
import java.sql.Statement;

/** Appointments class.
 * Used for storing all appointments.
 */
public class Appointments extends Lists {

    /** Stores the appointment Id. */
    private Integer appointmentId;
    /** Stores the title. */
    private String title;
    /** Stores the description. */
    private String description;
    /** Stores the location. */
    private String location;
    /** Stores the contact. */
    private String contact;
    /** Stores the type. */
    private String type;
    /** Stores the start date. */
    private String startDate;
    /** Stores the end date. */
    private String endDate;
    /** Stores the customer Id. */
    private Integer customerId;
    /** Stores the user Id. */
    private Integer userId;

    /** Appointments constructor used for storing all the appointment information.
     */
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

    /** Deletes an appointment. */
    public static void deleteAppointment(Appointments selectedAppointment) {
        Lists.appointmentList.remove(selectedAppointment);
    }
    /** Gets the appointment Id. */
    public Integer getAppointmentId() {return appointmentId;}
    /** Gets the title. */
    public String getTitle() {return title;}
    /** Gets the description. */
    public String getDescription() {return description;}
    /** Gets the location. */
    public String getLocation() {return location;}
    /** Gets the contact. */
    public String getContact() {return contact;}
    /** Gets the type. */
    public String getType() {return type;}
    /** Gets the start date. */
    public String getStartDate() {return startDate;}
    /** Gets the end date. */
    public String getEndDate() {return endDate;}
    /** Gets the customer ID. */
    public Integer getCustomerId() {return customerId;}
    /** Gets the user ID. */
    public Integer getUserId() {return userId;}

    /** Populates all of the appointments that are stored in the database. */
    public static void populateAppointments() {
        try {
            String query = "SELECT * FROM client_schedule.appointments";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Integer id = Integer.valueOf(result.getString("Appointment_ID"));
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                Integer customerId = Integer.valueOf(result.getString("Customer_ID"));
                Integer userId = Integer.valueOf(result.getString("User_ID"));
                Integer contactId = Integer.valueOf(result.getString("Contact_ID"));
                String contact = (String) Contacts.getName(contactId);
                String start =  result.getString("Start");

                String convertedStart = DateTime.toLocal(start);
                String end = result.getString("End");
                String convertedEnd = DateTime.toLocal(end);
                Appointments appointments = new Appointments(id, title, description, location, contact, type, convertedStart
                        , convertedEnd, customerId, userId);
                Main.setAppointmentId(id);
                Lists.appointmentList.add(appointments);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /** Sets the contact. */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /** Sets the title. */
    public void setTitle(String title) {
        this.title = title;
    }
    /** Sets the description. */
    public void setDescription(String description) {
        this.description = description;
    }
    /** Sets the location. */
    public void setLocation(String location) {
        this.location = location;
    }
    /** Sets the type. */
    public void setType(String type) {
        this.type = type;
    }
    /** Sets the start date. */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /** Sets the end date. */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /** Sets the customer ID. */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    /** Sets the user ID. */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}