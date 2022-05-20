package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    LocalDateTime nextApt = null;

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
        if (currentAppointment == null) {
            toYourProfile();
        } else {
            //Method found in utilities.methods.
            Stage stage = (Stage) (stageLabel).getScene().getWindow();
            passTheAppointment(currentAppointment, stage);
        }
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
        if (currentUser.getUserAppointments().size() > 0) {
            currentUser.getUserAppointments().clear();
        }
        //Populate the current user's associated appointments.
        if (currentUser.getUserAppointments().isEmpty()) {
            for (Appointment allAppointment : AllAppointments) {
                if (allAppointment.getUserID() == currentUser.getUserID()) {
                    currentUser.addUserAppointment(allAppointment);
                }
            }
        }
        if (currentUser.getUserAppointments().isEmpty()) {
            nextAptTimeTxt.setText("You don't have any upcoming Appointments.");
        }
        //Sort the associated appointments by datetime.
        currentUser.getUserAppointments().sort(Comparator.comparing(Appointment::getRawStart));
        //find next appointment. Then, finally, populate the text box.

        for (int i = 0; i < currentUser.getUserAppointments().size(); i++) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("    MMM dd yyyy   HH:mm");
            if (LocalDateTime.now().isBefore(currentUser.getUserAppointments().get(i).getRawStart())) {
                currentAppointment = currentUser.getUserAppointments().get(i);
                nextApt = currentUser.getUserAppointments().get(i).getRawStart();
                nextAptTimeTxt.setText("Your next appointment is scheduled for "
                        + timeFormatter.format(nextApt));
                //Send an alert if the next appointment is within 15 minutes.
                if (LocalDateTime.now().plusMinutes(15).isAfter(nextApt)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeight(550);
                    alert.setWidth(550);
                    int id = currentAppointment.getAppointmentID();
                    LocalDate d = currentAppointment.getParsedStartDate();
                    LocalTime t = currentAppointment.getParsedStartTime();
                    alert.setContentText("You have an upcoming appointment in less than 15 minutes \n" +
                            "\n" +
                            "Appointment ID :  " + id + "\n" +
                            "Start Date:  " + d + "\n" +
                            "Start Time:  " + t);
                    alert.show();
                    return;
                }
            }
            //If all appointments have passed, or there are no appointments, set a message to indicate that.
            else{
                nextAptTimeTxt.setText("You don't have any upcoming Appointments.");
            }
        }
        if((nextApt == null) || (nextApt.isAfter(LocalDateTime.now().plusMinutes(15)))){
            Alerts("You don't have any appointments in the next 15 minutes.");
        }
    }
}

