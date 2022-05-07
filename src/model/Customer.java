package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customer {
    //Create Customer class arguments.
    int customerID;
    String customerName;
    String address;
    String postalCode;
    String phone;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;
    int divisionID;

    /**
     * Create constructor for customer objects.
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone,
                    LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                    int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /**
     * Create getters and setters for customer objects.
     */
        public int getCustomerID() {
            return customerID;
        }

        public void setCustomerID(int customerID) {
            this.customerID = customerID;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public LocalDateTime getCreateDate() {
            return createDate;
        }

        public void setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public Timestamp getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(Timestamp lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public int getDivisionID() {
            return divisionID;
        }

        public void setDivisionID(int divisionID) {
            this.divisionID = divisionID;
        }

    /**
     * Create an associated list for customer appointments.
     */
    public static ObservableList<Appointment> customerAppointmentList = FXCollections.observableArrayList();

    /**
     * Method to add associated appointments.
     */
    void addAssociatedAppointment(Appointment appointment){
        customerAppointmentList.add(appointment);
    }

    /**
     * Method to delete an associated appointments.
     */
    public static void deleteCustomerAppointment(Appointment appointment){
        customerAppointmentList.remove(appointment);
    }

    /**
     * Method to return the observable list for associated appointments.
     */
    public ObservableList<Appointment> getAllCustomerAppointments(){
        return customerAppointmentList;
    }

    /**
     * Override to allow for readability in the combo boxes
     */
    @Override
    public String toString(){
        return (customerName + "   " + "ID: " + customerID);
    }
}
