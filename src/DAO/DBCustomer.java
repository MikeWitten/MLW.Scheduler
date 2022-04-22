package DAO;

import model.Customer;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static utilities.Methods.addCustomer;

public abstract class DBCustomer {

    /**
     * Method to insert a customer into the customer database.
     */
    public static void insertCustomer(Customer customer) throws SQLException {
        //Convert to UTC time zone.
        ZonedDateTime rawCreateDate = ZonedDateTime.of(customer.getCreateDate(), ZoneId.systemDefault());
        ZonedDateTime convCreateDate = rawCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = convCreateDate.toLocalDateTime();
        Timestamp rawLastUpdate = customer.getLastUpdate();
        ZonedDateTime zonedUpdate = ZonedDateTime.ofInstant(rawLastUpdate.toInstant(),ZoneId.systemDefault());
        ZonedDateTime convUpdate = zonedUpdate.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp lastUpdate = Timestamp.valueOf(convUpdate.toLocalDateTime());

        //I get all the values from the passed customer object.
        String customerName = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();
        String createdBy = customer.getCreatedBy();
        String lastUpdatedBy = customer.getLastUpdatedBy();
        int divisionID = customer.getDivisionID();

        //My prepared statement passes in values for everything except the customer ID so that the
        //database will autogenerate the ID for me.
        String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address , Postal_Code ," +
                " Phone, Create_Date , Created_By , Last_Update , Last_Updated_By , Division_ID)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(createDate));
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);

        //Execute the update.
        ps.executeUpdate();
    }

    /**
     * Method to update an existing customer in the database.
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        //Convert to UTC time zone.
        ZonedDateTime rawCreateDate = ZonedDateTime.of(customer.getCreateDate(), ZoneId.systemDefault());
        ZonedDateTime convCreateDate = rawCreateDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime createDate = convCreateDate.toLocalDateTime();
        Timestamp rawLastUpdate = customer.getLastUpdate();
        ZonedDateTime zonedUpdate = ZonedDateTime.ofInstant(rawLastUpdate.toInstant(),ZoneId.systemDefault());
        ZonedDateTime convUpdate = zonedUpdate.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp lastUpdate = Timestamp.valueOf(convUpdate.toLocalDateTime());

        //I get all the values from the passed customer object.
        String customerName = customer.getCustomerName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhone();
        String createdBy = customer.getCreatedBy();
        String lastUpdatedBy = customer.getLastUpdatedBy();
        int divisionID = customer.getDivisionID();
        int customerID = customer.getCustomerID();

        //My prepared statement passes in all values from the customer object.
        String sql = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?," +
                " Postal_Code = ?, Phone = ?,Create_Date = ?, Created_By = ?, Last_Update = ?," +
                " Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(createDate));
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.setInt(10, customerID);

        //Execute the update.
        ps.executeUpdate();
    }

    /**
     * Method to delete a customer from the database.
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        //Get the ID from the customer object.
        int customerID = customer.getCustomerID();

        //Create the Prepared Statement.
        String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);

        //Execute the delete.
        ps.executeUpdate();
    }

    /**
     * Method to populate tables with all customer objects from the database.
     */
    public static void selectAllCustomers() throws SQLException {
        //Create the prepared statement.
        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        //Iterate through all the database objects until the next() value returns false.
        //Execute the query.
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            int customerID = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String address = resultSet.getString("Address");
            String postalCode = resultSet.getString("Postal_Code");
            String phone = resultSet.getString("Phone");
            String createdBy = resultSet.getString("Created_By");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int divisionID = resultSet.getInt("Division_ID");
            //Convert times to local time.
            Timestamp createDateTS = resultSet.getTimestamp("Create_Date");
            LocalDateTime ldtUtcCreateDate = createDateTS.toLocalDateTime();
            ZonedDateTime utcCreateDate = ZonedDateTime.of(ldtUtcCreateDate, ZoneId.of("UTC"));
            LocalDateTime createDate = utcCreateDate.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            Timestamp rawLastUpdate = resultSet.getTimestamp("Last_Update");
            LocalDateTime ldtLastUpdateUTC = rawLastUpdate.toLocalDateTime();
            ZonedDateTime zdtLastUpdateUTC = ZonedDateTime.of(ldtLastUpdateUTC, ZoneId.of("UTC"));
            ZonedDateTime zdtLastUpdateHere = zdtLastUpdateUTC.withZoneSameInstant(ZoneId.systemDefault());
            Timestamp lastUpdate = Timestamp.valueOf(zdtLastUpdateHere.toLocalDateTime());
            //Create customer objects and add them to the customers list.
            Customer customer = new Customer(customerID, customerName, address, postalCode, phone,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            addCustomer(customer);
        }
    }
}
