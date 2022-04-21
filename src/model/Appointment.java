package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    //Create Appointment class arguments.
    int appointmentID;
    String title;
    String description;
    String location;
    String type;
    LocalDateTime rawStart;
    LocalDate parsedStartDate;
    LocalTime parsedStartTime;
    LocalDateTime rawEnd;
    LocalDate parsedEndDate;
    LocalTime parsedEndTime;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;
    int customerID;
    int userID;
    int contactID;


    /**
     * Create a constructor for the Appointment objects.
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime rawStart, LocalDate parsedStartDate, LocalTime parsedStartTime,
                       LocalDateTime rawEnd, LocalDate parsedEndDate, LocalTime parsedEndTime,
                       LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                       int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.rawStart = rawStart;
        this.parsedStartDate = parsedStartDate;
        this.parsedStartTime = parsedStartTime;
        this.rawEnd = rawEnd;
        this.parsedEndDate = parsedEndDate;
        this.parsedEndTime = parsedEndTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    /**
     * Create getters and setters for the Appointment objects.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getRawStart() {
        return rawStart;
    }

    public void setRawStart(LocalDateTime rawStart) {
        this.rawStart = rawStart;
    }

    public LocalDate getParsedStartDate() {
        return parsedStartDate;
    }

    public void setParsedStartDate(LocalDate parsedStartDate) {
        this.parsedStartDate = parsedStartDate;
    }

    public LocalTime getParsedStartTime() {
        return parsedStartTime;
    }

    public void setParsedStartTime(LocalTime parsedStartTime) {
        this.parsedStartTime = parsedStartTime;
    }

    public LocalDateTime getRawEnd() {
        return rawEnd;
    }

    public void setRawEnd(LocalDateTime rawEnd) {
        this.rawEnd = rawEnd;
    }

    public LocalDate getParsedEndDate() {
        return parsedEndDate;
    }

    public void setParsedEndDate(LocalDate parsedEndDate) {
        this.parsedEndDate = parsedEndDate;
    }

    public LocalTime getParsedEndTime() {
        return parsedEndTime;
    }

    public void setParsedEndTime(LocalTime parsedEndTime) {
        this.parsedEndTime = parsedEndTime;
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

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
