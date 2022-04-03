package DAO;

import model.Country;
import utilities.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

import static utilities.Methods.addCountry;

public abstract class DBCountry {

    /**
     * Method to populate a list for reference.
     */
    public static void selectAllCountries()throws SQLException{
        //Create a prepared statement.
        String sql = "SELECT * FROM client_schedule.countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        //Iterate through all the database objects until the next() value returns false.
        //Execute the query.
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            int countryID = resultSet.getInt("Country_ID");
            String country = resultSet.getString("Country");
            Timestamp createDateTS = resultSet.getTimestamp("Create_Date");
            LocalDateTime createDate = createDateTS.toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");

            //Create objects and populate the countries list.
            Country newCountry = new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
            addCountry(newCountry);
        }
    }
}
