package DAO;

import model.Division;
import utilities.JDBC;
import java.sql.*;
import java.time.LocalDateTime;
import static utilities.Methods.addDivision;

public class DBDivision {
    /**
     * Method to create a list of divisions for reference and table population.
     */
    public static void selectAllDivisions() throws SQLException {
        //Create a prepared statement
        String sql = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Iterate through all the database objects until the next() value returns false.
        //Execute the query.
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            int divisionID = resultSet.getInt("Division_ID");
            String division = resultSet.getString("Division");
            Timestamp createDateTS = resultSet.getTimestamp("Create_Date");
            LocalDateTime createDate = createDateTS.toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int countryID = resultSet.getInt("Country_ID");
            //Create division objects and add them to the division list.
            Division newDivision = new Division(divisionID, division, createDate, createdBy, lastUpdate,
                    lastUpdatedBy, countryID);
            addDivision(newDivision);
        }
    }
}
