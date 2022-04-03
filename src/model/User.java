package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    //Create User object arguments.
    int userID;
    String userName;
    String password;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;

    /**
     * Create a constructor for User objects.
     */
    public User(int userID, String userName, String password, LocalDateTime createDate, String createdBy,
                Timestamp lastUpdate, String lastUpdatedBy) {
        this.userID = userID;                       //FIXME unique value
        this.userName = userName;                   //FIXME unique value 50 char max
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;                 //FIXME 50 char max
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;         //FIXME 50 char max
    }

    /**
     * Create getters and setters for User objects.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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
     * Create a list for associated appointments
     */
    static ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();

    /**
     * Method to add an appointment to the appointments list.
     */
    public void addUserAppointment(Appointment appointment){
        userAppointments.add(appointment);
    }

    /**
     * Method to delete an appointment from appointments list.
     */
    public static void deleteUserAppointment(Appointment appointment){
        userAppointments.remove(appointment);
    }

    /**
     * Method to show user appointments.
     */
    public ObservableList<Appointment> getUserAppointments(){
        return userAppointments;
    }
}
