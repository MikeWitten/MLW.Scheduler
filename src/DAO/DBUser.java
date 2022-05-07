package DAO;

import model.User;
import utilities.JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static utilities.Methods.addUser;

public class DBUser {
    /**
     * Method to populate a list for reference and validation purposes.
     */
    public static void selectAllUsers()throws SQLException {
        //Create a prepared statement.
        String sql = "SELECT * FROM client_schedule.users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Iterate through all the database objects until the next() value returns false.
        //Execute the query.
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            int userID = resultSet.getInt("User_ID");
            String userName = resultSet.getString("User_Name");
            String password = resultSet.getString("Password");
            Timestamp createDateTS = resultSet.getTimestamp("Create_Date");
            LocalDateTime createDate = createDateTS.toLocalDateTime();
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            //Create user objects and populate the user list.
            User user = new User(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
            addUser(user);
        }

    }
}
