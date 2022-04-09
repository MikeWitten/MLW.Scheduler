package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class HomePage implements Initializable {

    public Label stageLabel;
    public TextField userNameTxt;
    public TextField nextAptTimeTxt;



    //Navigation
    public void toExit() {
        exitHere();
    }

    public void toLogOut() {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        logOutHere(stage);
    }

    public void toYourProfile() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Profile.fxml");
    }

    public void toAppointmentManager() throws IOException{
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Appointments.fxml");
    }

    public void toCustomerManager() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Customers.fxml");
    }

    public void toHome() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    }

    public void toNextAppointment() throws IOException {
        //FIXME pass the appointment football
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Appointment Details.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Find the user based on the active user.
       int id = getActiveUser(null).getUserID();
       User temp = null;
        for (User allUser : AllUsers) {
            if (id == allUser.getUserID()) {
                temp = allUser;
            }
        }

        //Populate the associated user table for the active user.
        for (model.Appointment allAppointment : AllAppointments) {
            assert temp != null;
            if (allAppointment.getUserID() == temp.getUserID()) {
                temp.addUserAppointment(allAppointment);
            }
        }

        //Sort the associated list for the active user.




        userNameTxt.setText("Welcome back " + (getActiveUser(null).getUserName()));
    }
}

