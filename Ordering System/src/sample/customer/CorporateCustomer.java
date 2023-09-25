package sample.customer;

public class CorporateCustomer extends Customer{

    private int creditLimit;
    private int employeeId;
    private int customerId;
    private String contactName=null;



    @Override
    public String showCustomerDetails() {
       name=contactName;
        return name;
    }

    //getters
    public String getName() {
        return contactName;
    }

    public String getAddress() {
        return address;
    }

    public int getCrediLimit() {
        return creditLimit;
    }



    public int getEmployeeId() {
        return employeeId;
    }

    public int getId() {
        return customerId;
    }


    //setters
    public void setName(String name) {
        contactName = name;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setId(int id) {
        this.customerId = id;
    }

}
