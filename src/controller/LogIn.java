package controller;

import DAO.DBContact;
import DAO.DBCountry;
import DAO.DBCustomer;
import DAO.DBDivision;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class LogIn implements Initializable {
    public TextField userNameTxt;
    public TextField passwordTxt;
    public Label stageLabel;    //Use to get stage for navigation.
    public TextField zoneIDTxt;
    public TextField dateTimeTxt;



    /**
     * Check credentials and navigate to home page.
     */
    public void credentialCheck() throws IOException, SQLException {
        //create a user variable.
        User user1 = null;

        //Check to ensure both userName and password fields are filled in.
        if(userNameTxt.getText().isEmpty() || passwordTxt.getText().isEmpty()){
            Alerts("User Name and Password required");
            return;
        }

        //Iterate through the users list and find the username, Assign the valid object to the user1 variable.
        int i;
        for (i=0; i < AllUsers.size(); i++){
            if(AllUsers.get(i).getUserName().equals(userNameTxt.getText())){
               user1 = AllUsers.get(i);
            }
        }
        //If no user is found alert for invalid credentials.
        if (user1 == null){
            Alerts ("User Name and Password required");
        }
        //If the User is valid check to make sure the user ID and password matches.
        else if (user1.getPassword().equals(passwordTxt.getText())){
            //Assign an active user for navigation and field population purposes.
            getActiveUser(user1);

            //Navigate to the main page.
            Stage stage = (Stage) stageLabel.getScene().getWindow();
            navigation(stage, "/view/Home Page.fxml");
            JDBC.openConnection();
            //Create lists to populate tables and get information throughout the program.
            DBContact.selectAllContacts();
            DBCountry.selectAllCountries();
            DBCustomer.selectAllCustomers();
            DBDivision.selectAllDivisions();
        }
        //If UserID and Password do not match alert for invalid credentials.
        else {
            Alerts("User Name and Password required");
        }
    }

    /**
     * Exit the program.
     */
    public void toExit() {
        //Method found in Utilities.methods
        exitHere();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populate the zone and time fields.
        zoneIDTxt.setText(ZoneId.systemDefault().toString());
        DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("MMM dd yyyy   HH:mm");
        dateTimeTxt.setText(timeFormatter.format(LocalDateTime.now()));


    }
}
