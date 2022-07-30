package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PropAppointment extends Appointment{
    /**
     * Create a constructor for the Appointment objects.
     */
    public PropAppointment(int appointmentID, String title, String description, String location, AptType type, LocalDateTime rawStart, LocalDate parsedStartDate, LocalTime parsedStartTime, LocalDateTime rawEnd, LocalDate parsedEndDate, LocalTime parsedEndTime, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        super(appointmentID, title, description, location, type, rawStart, parsedStartDate, parsedStartTime, rawEnd, parsedEndDate, parsedEndTime, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
    }

}
