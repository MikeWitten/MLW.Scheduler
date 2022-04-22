package DAO;

import model.Country;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
            LocalDateTime ldtCreateDateUTC = createDateTS.toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdateTS = resultSet.getTimestamp("Last_Update");
            LocalDateTime ldtLastUpdateUTC = lastUpdateTS.toLocalDateTime();
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            //Convert times to local time
            ZonedDateTime utcCreateDate = ZonedDateTime.of(ldtCreateDateUTC, ZoneId.of("UTC"));
            LocalDateTime createDate = utcCreateDate.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            ZonedDateTime utcLastUpdate = ZonedDateTime.of(ldtLastUpdateUTC, ZoneId.of("UTC"));
            LocalDateTime ldtLastUpdateHere = utcLastUpdate.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            Timestamp lastUpdate = Timestamp.valueOf(ldtLastUpdateHere);
            //Create objects and populate the countries list.
            Country newCountry = new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
            addCountry(newCountry);
        }
    }
}
