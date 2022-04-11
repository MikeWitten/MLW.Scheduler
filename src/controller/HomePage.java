package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class HomePage implements Initializable {

    public Label stageLabel;
    public TextField nextAptTimeTxt;
    public Label welcomeLabel;
    Appointment currentAppointment;


    //Navigation
    public void toExit() {
        //Method found in utilities.methods.
        exitHere();
    }

    public void toLogOut() {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        logOutHere(stage);
    }

    public void toYourProfile() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Profile.fxml");
    }

    public void toAppointmentManager() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Appointments.fxml");
    }

    public void toCustomerManager() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Customers.fxml");
    }

    public void toHome() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    }

    public void toNextAppointment() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) (stageLabel).getScene().getWindow();
        passTheAppointment(currentAppointment, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set a personalized welcome message.
        welcomeLabel.setText("Welcome Back " + getActiveUser(null).getUserName() + "!!!");

        //The following 33 lines of code were written to populate a completely unnecessary
        //text box that will display the next upcoming appointment. I would've just deleted it, but it proved to be
        //annoyingly problematic, so I decided to leave it in.

        //Identify the current user.
        User currentUser = null;
        for (User allUser : AllUsers) {
            if (allUser.getUserID() == getActiveUser(null).getUserID()) {
                currentUser = allUser;
            }
        }

        //If another user was logged on previously, clear their associated appointments.
        assert currentUser != null;
        if (currentUser.getUserAppointments().size() > 0 ){
            currentUser.getUserAppointments().clear();
        }

        //Populate the current user's associated appointments.
        if(currentUser.getUserAppointments().isEmpty()) {
           for (model.Appointment allAppointment : AllAppointments) {
               if (allAppointment.getUserID() == currentUser.getUserID()) {
                   currentUser.addUserAppointment(allAppointment);
                }
            }
        }

        //Sort the associated appointments by datetime.
        currentUser.getUserAppointments().sort(Comparator.comparing(Appointment::getStart));

        //find next appointment. Then, finally, populate the text box.
        LocalDateTime nextApt;
        for (int i = 0; i < currentUser.getUserAppointments().size(); i++) {
            DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("    MMM dd yyyy   HH:mm");
            if(LocalDateTime.now().isBefore(currentUser.getUserAppointments().get(i).getStart())){
                currentAppointment = currentUser.getUserAppointments().get(i);
                nextApt = currentUser.getUserAppointments().get(i).getStart();
                nextAptTimeTxt.setText("Your next appointment is scheduled for "
                        + timeFormatter.format(nextApt));

                //Send an alert if the next appointment is within 15 minutes.
                    if (LocalDateTime.now().plusMinutes(15).isAfter(nextApt)){
                        Alerts("You have an upcoming appointment");
                    }
                return;
            }
            //If all appointments have passed, or there are no appointments, set a message to indicate that.
            else nextAptTimeTxt.setText("You don't have any upcoming Appointments.");
        }
    }
}

