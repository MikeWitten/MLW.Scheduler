package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Country {

    //Create Country object arguments.
    int countryID;
    String country;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;

    /**
     * Create a constructor for the Country objects.
     */
    public Country(int countryID, String country, LocalDateTime createDate, String createdBy,
                   Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }



    /**
     * Create getters and setters for the Country objects.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getCountry() {
        return country;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Create a list of associated First Level Divisions
     */
    ObservableList<Division> divisions = FXCollections.observableArrayList();

    /**
     * Method to get the first Level Division list.
     */
    ObservableList<Division> getFirstLevelDivisions(){
        return divisions;
    }

    /**
     * Create a list of associated customers.
     */
    static ObservableList<Customer> countryCustomers = FXCollections.observableArrayList();

    /**
     * Method to add a customer to associated list.
     */
    public  void addCountryCustomer(Customer customer){
        countryCustomers.add(customer);
    }

    /**
     * Method to delete a customer from the associated list.
     */
    public static void deleteCountryCustomer(Customer customer){
        countryCustomers.remove(customer);
    }

    /**
     * Method to get country customers list.
     */
    ObservableList<Customer> getCountryCustomers(){
        return countryCustomers;
    }
}
