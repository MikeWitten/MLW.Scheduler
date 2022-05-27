package DAO;

import model.Appointment;
import utilities.JDBC;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static utilities.Methods.addApt;

public abstract class DBAppointment {
    /**
     * Method to insert an appointment into the database.
     */
    public static void insertApt(Appointment appointment) throws SQLException {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        //Convert time values to UTC for storage in the database.
        ZoneId utcID = ZoneId.of("UTC");
        Timestamp rawTS = appointment.getLastUpdate();
        ZonedDateTime rawZDT = ZonedDateTime.ofInstant(rawTS.toInstant(), ZoneId.systemDefault());
        ZonedDateTime convZDT = rawZDT.withZoneSameInstant(utcID);
        Timestamp lastUpdate = Timestamp.valueOf(convZDT.toLocalDateTime());

        LocalDateTime rawStart = appointment.getRawStart();
        ZonedDateTime startZDT = ZonedDateTime.of(rawStart, ZoneId.systemDefault());
        LocalDateTime start = startZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        LocalDateTime rawEnd = appointment.getRawEnd();
        ZonedDateTime endZDT = ZonedDateTime.of(rawEnd, ZoneId.systemDefault());
        LocalDateTime end = endZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        LocalDateTime createDate =ZonedDateTime.of(appointment.getCreateDate(), ZoneId.systemDefault()).withZoneSameInstant(utcID).toLocalDateTime();
        //Take values from an Appointment object that has a placeholder appointmentID.
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        String createdBy = appointment.getCreatedBy();
        String lastUpdatedBy = appointment.getLastUpdatedBy();
        int customerID = appointment.getCustomerID();
        int userID = appointment.getUserID();
        int contactID = appointment.getContactID();
        //My prepared statement passes in values for everything except the appointment ID so that the
        // Database will autogenerate the ID for me.
        String sql = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start," +
                " End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(f.format(start)));
        ps.setTimestamp(6,Timestamp.valueOf(f.format(end)));
        ps.setTimestamp(7,Timestamp.valueOf((createDate)));
        ps.setString(8, createdBy);
        ps.setTimestamp(9,lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11,customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);
        //Execute the update.
        ps.executeUpdate();
    }

    /**
     * Method to update an existing appointment object in the database.
     */
    public static void updateApt(Appointment appointment) throws SQLException {
         //Convert time values to UTC for storage in the database.
        ZoneId utcID = ZoneId.of("UTC");
        Timestamp rawTS = appointment.getLastUpdate();
        ZonedDateTime rawZDT = ZonedDateTime.ofInstant(rawTS.toInstant(), ZoneId.systemDefault());
        ZonedDateTime convZDT = rawZDT.withZoneSameInstant(utcID);
        Timestamp lastUpdate = Timestamp.valueOf(convZDT.toLocalDateTime());
        LocalDateTime start = ZonedDateTime.of(appointment.getRawStart(), ZoneId.systemDefault()).withZoneSameInstant(utcID).toLocalDateTime();
        LocalDateTime end = ZonedDateTime.of(appointment.getRawEnd(), ZoneId.systemDefault()).withZoneSameInstant(utcID).toLocalDateTime();
        LocalDateTime createDate =ZonedDateTime.of(appointment.getCreateDate(), ZoneId.systemDefault()).withZoneSameInstant(utcID).toLocalDateTime();
        //I get the values from an Appointment object.
        int appointmentID = appointment.getAppointmentID();
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        String createdBy = appointment.getCreatedBy();
        String lastUpdatedBy = appointment.getLastUpdatedBy();
        int customerID = appointment.getCustomerID();
        int userID = appointment.getUserID();
        int contactID = appointment.getContactID();
        //The prepared statement passes all values from the appointment object.
        String sql = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?," +
                " Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?," +
                " Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6,Timestamp.valueOf(end));
        ps.setTimestamp(7,Timestamp.valueOf((createDate)));
        ps.setString(8, createdBy);
        ps.setTimestamp(9,lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11,customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);
        ps.setInt(14, appointmentID);
        //Execute the update
        ps.executeUpdate();
    }

    /**
     * Method to delete an appointment object from the database.
     */
    public static void deleteApt(Appointment appointment) throws SQLException {
        //Get the ID from the appointment object.
        int appointmentID = appointment.getAppointmentID();
        //Create the prepared statement.
        String sql = "DELETE FROM client_schedule.appointments Where Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        //Execute the update.
        ps.executeUpdate();
    }

    /**
     * Method to populate tables with all appointment objects.
     */
    public static void selectAllAppointments() throws SQLException {
        //Create the prepared statement.
        String sql = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Iterate through all the database objects until the next() value returns false.
        //Execute the query.
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            Timestamp startTS = resultSet.getTimestamp("Start");
            LocalDateTime rawStart = startTS.toLocalDateTime();
            Timestamp endTS = resultSet.getTimestamp("End");
            LocalDateTime rawEnd = endTS.toLocalDateTime();
            Timestamp createDateTS = resultSet.getTimestamp("Create_Date");
            LocalDateTime RawCreateDate = createDateTS.toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp rawLastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            //convert UTC database time to local zoned times.
            ZoneId utcZone = ZoneId.of("UTC");
            ZonedDateTime zonedStartTime =ZonedDateTime.of(rawStart, utcZone);
            ZonedDateTime zonedEndTime = ZonedDateTime.of(rawEnd, utcZone);
            ZonedDateTime zonedCreateDate = ZonedDateTime.of(RawCreateDate, utcZone);
            ZonedDateTime zonedLastUpdate = ZonedDateTime.ofInstant(rawLastUpdate.toInstant(), utcZone);
            ZoneId hereZone = ZoneId.systemDefault();
            LocalDateTime start = zonedStartTime.withZoneSameInstant(hereZone).toLocalDateTime();
            LocalDateTime end = zonedEndTime.withZoneSameInstant(hereZone).toLocalDateTime();
            LocalDateTime createDate = zonedCreateDate.withZoneSameInstant(hereZone).toLocalDateTime();
            Timestamp lastUpdate = Timestamp.valueOf(zonedLastUpdate.withZoneSameInstant(hereZone).toLocalDateTime());
            LocalDate parsedStartDate = start.toLocalDate();
            LocalTime parsedStartTime = start.toLocalTime();
            LocalDate parsedEndDate = end.toLocalDate();
            LocalTime parsedEndTime = end.toLocalTime();

            Appointment appointment = new Appointment(appointmentID, title, description, location,
                    type, start, parsedStartDate, parsedStartTime, end, parsedEndDate, parsedEndTime,  createDate, createdBy,lastUpdate, lastUpdatedBy, customerID,
                    userID, contactID);
            addApt(appointment);
        }
    }


}
