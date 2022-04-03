package DAO;

import model.Appointment;
import utilities.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

import static utilities.Methods.addApt;

public abstract class DBAppointment {

    /**
     * Method to insert an appointment into the database.
     */
    public static void insertApt(Appointment appointment) throws SQLException {

        //I get the values from an Appointment object with a placeholder appointmentID.
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();
        LocalDateTime createDate = appointment.getCreateDate();
        String createdBy = appointment.getCreatedBy();
        Timestamp lastUpdate = appointment.getLastUpdate();
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
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6,Timestamp.valueOf(end));
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

        //I get the values from an Appointment object.
        int appointmentID = appointment.getAppointmentID();
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();
        LocalDateTime createDate = appointment.getCreateDate();
        String createdBy = appointment.getCreatedBy();
        Timestamp lastUpdate = appointment.getLastUpdate();
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
            LocalDateTime start = startTS.toLocalDateTime();
            Timestamp endTS = resultSet.getTimestamp("End");
            LocalDateTime end = endTS.toLocalDateTime();
            Timestamp createDateTS = resultSet.getTimestamp("Create_Date");
            LocalDateTime createDate = createDateTS.toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, title, description, location,
                    type, start, end, createDate, createdBy,lastUpdate, lastUpdatedBy, customerID,
                    userID, contactID);
            addApt(appointment);

            System.out.print(appointmentID + " | ");
            System.out.print(title + " | ");
            System.out.print(description + " | ");
            System.out.print(location + " | ");
            System.out.print(type + " | ");
            System.out.print(start + " | ");
            System.out.print(end + " | ");
            System.out.print(createDate + " | ");
            System.out.print(createdBy + " | ");
            System.out.print(lastUpdate + " | ");
            System.out.print(lastUpdatedBy + " | ");
            System.out.print(customerID + " | ");
            System.out.print(userID + " | ");
            System.out.print(contactID + "\n");

        }
    }


}
