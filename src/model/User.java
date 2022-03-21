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
     * Create getters for User objects (addition of users is beyond the scope of this application).
     */
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
