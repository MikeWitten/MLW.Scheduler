package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class AppointmentDetails implements Initializable {
    public Label stageLabel;
    public TextField appointmentIDTxt;
    public TextField titleTxt;
    public TextField locationTxt;
    public TextField typeTxt;
    public TextField startTimeTxt;
    public TextField endTimeTxt;
    public TextField createDateTxt;
    public TextField createdByTxt;
    public TextField lastUpdatedTxt;
    public TextField lastUpdatedByTxt;
    public TextField customerIDTxt;
    public TextField userIDTxt;
    public TextField contactIDTxt;
    public TextArea descriptionTxt;
    Appointment currentAppointment;

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

    public void toCustomerDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Customer Details.fxml");
    } //FIXME pass the customer football

    public void toUserDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/User Details.fxml");
    } //FIXME pass the user football

    public void toContactDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Contact Details.fxml");
    } //FIXME pass the contact football

    /**
     * Receive the appointment from the previous stage.
     */
    public void receiveAppointment(Appointment appointment){
        currentAppointment = appointment;

        //Set fields with current appointment data.
        appointmentIDTxt.setText(String.valueOf(currentAppointment.getAppointmentID()));
        titleTxt.setText(currentAppointment.getTitle());
        locationTxt.setText(currentAppointment.getLocation());
        typeTxt.setText(currentAppointment.getType());
        startTimeTxt.setText(String.valueOf(currentAppointment.getStart()));
        endTimeTxt.setText(String.valueOf(currentAppointment.getEnd()));
        createDateTxt.setText(String.valueOf(currentAppointment.getCreateDate()));
        createdByTxt.setText(currentAppointment.getCreatedBy());
        lastUpdatedTxt.setText(String.valueOf(currentAppointment.getLastUpdate()));
        lastUpdatedByTxt.setText(currentAppointment.getLastUpdatedBy());
        customerIDTxt.setText(String.valueOf(currentAppointment.getCustomerID()));
        userIDTxt.setText(String.valueOf(currentAppointment.getUserID()));
        contactIDTxt.setText(String.valueOf(currentAppointment.getContactID()));
        descriptionTxt.setText(currentAppointment.getDescription());
    }

    /**
     * Receive the user information from the previous stage.
     */
    public void receiveUser(User user) {
        lastUpdatedByTxt.setText(user.getUserName());
        lastUpdatedTxt.setText(String.valueOf(Timestamp.from(Instant.now())));
        createdByTxt.setText(user.getUserName());
        createDateTxt.setText(String.valueOf(LocalDateTime.now()));
    }

    /**
     * Method to add an appointment.
     */
    public void addAppointment() {
    } //FIXME

    /**
     * Method to edit an existing appointment.
     */
    public void editAppointment() {
    } //FIXME

    /**
     * Method to delete an appointment.
     */
    public void deleteAppointment() {
    } //FIXME

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void makeEditable() {
    } //FIXME


    public void saveChanges() {
    } //FIXME

    public void clearForm() {
    } //FIXME
}
