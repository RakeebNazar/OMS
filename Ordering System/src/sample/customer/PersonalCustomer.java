package sample.customer;

public class PersonalCustomer extends Customer {


    private long crediCardNumber;
    private int employeeId;
    private int customerId;



    @Override
    public String showCustomerDetails() {
        return name;
    }

    //getters
    public String getContactName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getCrediCardNumber() {
        return crediCardNumber;
    }


    public int getId() {
        return customerId;
    }





    //setters
    public void setContactName(String contactName) {
        this.name = contactName;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public void setCrediCardNumber(long crediCardNumber) {
        this.crediCardNumber = crediCardNumber;
    }

    public void setId(int id) {
        this.customerId = id;
    }

}
