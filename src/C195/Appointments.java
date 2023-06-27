package C195;

public class Appointments extends Lists {

    private Integer appointmentId;

    private final String title;
    private final String description;
    private final String location;

    private final String contact;

    private final String type;

    private final String startDate;

    private final String endDate;

    private final String customerId;

    private final String userId;



    Appointments(Integer appointmentId, String title, String description, String location, String contact, String type, String startDate, String endDate, String customerId, String userId) {
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

    public String getCustomerId() {return customerId;}

    public String getUserId() {return userId;}


}