package DAO;

import model.Contact;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utilities.Methods.addContact;

public abstract class DBContact {
    /**
     * Method to get contact information for an observable list.
     */
    public static void selectAllContacts()throws SQLException{
        //Create the prepared statement.
        String sql = "SELECT * FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        //Iterate through all the database objects until the next() value returns false.
        //Execute the query.
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            int contact_ID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");

            //Create objects and add them to the contacts list
            Contact contact = new Contact(contact_ID, contactName, email);
            addContact(contact);
        }
    }
}
