package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Division {
    //Create First Level Division object arguments.
    int divisionID;
    String division;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;
    int CountryID;

    /**
     * Create a constructor for the First Level Division objects.
     */
    public Division(int divisionID, String division, LocalDateTime createDate, String createdBy,
                    Timestamp lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.CountryID = countryID;
    }



    /**
     * Create getters and setters for the First Level Division objects.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public void setCountryID(int countryID) {
        CountryID = countryID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
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

    public int getCountryID() {
        return CountryID;
    }

    /**
     * Create a list of associated customers for the first level division objects.
     */
    static ObservableList<Customer> divisionCustomerList = FXCollections.observableArrayList();

    /**
     * Method to add a customer to the associated list.
     */
    public void addDivisionCustomer(Customer customer){
        divisionCustomerList.add(customer);
    }

    /**
     * Method to delete a customer from the associated list.
     */
    public static void deleteDivisionCustomer(Customer customer){
        divisionCustomerList.remove(customer);
    }

    /**
     * Method to show all customers in the division.
     */
    public ObservableList<Customer> getDivisionCustomerList() {
        return divisionCustomerList;
    }

    @Override
    public String toString(){
        return (division);
    }
}
