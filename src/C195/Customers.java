package C195;

public class Customers {

    private final Integer customerId;

    private final String name;
    private final String address;
    private final Integer zip;

    private final Integer phone;

    private final String state;

    private final String country;



    Customers(Integer id, String name, String address, Integer zip, Integer phone, String state, String country) {
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

    public Integer getZip() {return zip;}

    public Integer getPhone() {return phone;}

    public String getState() {return state;}

    public String getCountry() {return country;}



}
