package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FirstLevelDivision {
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
    public FirstLevelDivision(int divisionID, String division, LocalDateTime createDate, String createdBy,
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
     * Create getters for the First Level Division objects (no setters since this class is read only).
     */
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
    ObservableList<Customer> divisionCustomerList = FXCollections.observableArrayList();

    /**
     * Method to add a customer to the associated list.
     */
    public void addDivisionCustomer(Customer customer){
        divisionCustomerList.add(customer);
    }

    /**
     * Method to delete a customer from the associated list.
     */
    public void deleteDivisionCustomer(Customer customer){
        divisionCustomerList.remove(customer);
    }

    /**
     * Method to show all customers in the division.
     */
    public ObservableList<Customer> getDivisionCustomerList() {
        return divisionCustomerList;
    }
}
