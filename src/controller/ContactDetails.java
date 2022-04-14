package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class ContactDetails implements Initializable {

    public Label stageLabel;
    public TextField contactIDTxt;
    public TextField contactNameTxt;
    public TextField emailTxt;
    public ButtonBar buttonBar;
    static Contact currentContact;


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

    public void toAppointmentDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    } //FIXME pass the appointment football.

    public void toHome() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    }

    /**
     * Receive the contact football.
     */
    public void receiveTheContact(Contact contact) {
        //set the current contact for reference.
        currentContact = contact;

        //Set the text fields from the contact data.
        contactNameTxt.setText(currentContact.getContactName());
        contactIDTxt.setText(String.valueOf(currentContact.getContactID()));
        emailTxt.setText(currentContact.getEmail());

        //populate the associated appointments list.  //FIXME

        //populate the appointment table.    //FIXME
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

    /**
     * Create an appointment table.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> customerName;
    public TableColumn<Object, Object> startTime;
    public TableColumn<Object, Object> endTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
