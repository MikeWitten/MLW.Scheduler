package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    int customerID;
    String name;
    String address;
    int postalCode;
    String country;
    String state;

    public Customer(int customerID, String name,String address,int postalCode,String country,String state){
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.country = country;
        this.state = state;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public final ObservableList<Appointment> CustomerAppointments = FXCollections.observableArrayList();

    void addAppointment(Appointment appointment){
        CustomerAppointments.add(appointment);
    }

    void removeAppointment(Appointment appointment){
        CustomerAppointments.remove(appointment);
    }

    public ObservableList<Appointment> getAllAppointments() {
        return CustomerAppointments;
    }

}
