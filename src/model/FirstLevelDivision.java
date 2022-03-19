package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FirstLevelDivision {
    //Create First Level Division object arguments.
    int divisionID;                 //FIXME unique value 10 char max
    String division;                //FIXME 50 char max Alert(already exists)
    LocalDateTime createDate;
    String createdBy;               //FIXME 50 char max
    Timestamp lastUpdate;
    String lastUpdatedBy;           //FIXME 50 char max
    int CountryID;                  //FIXME unique value 10 char max

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
        CountryID = countryID;
    }

    /**
     * Create getters and setters for the First Level Division objects.
     */
    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public int getCountryID() {
        return CountryID;
    }

    public void setCountryID(int countryID) {
        CountryID = countryID;
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
