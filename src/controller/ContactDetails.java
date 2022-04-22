package controller;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;

import static utilities.Methods.*;

public class ContactDetails {

    public Label stageLabel;
    public TextField contactIDTxt;
    public TextField contactNameTxt;
    public TextField emailTxt;
    public ButtonBar buttonBar;
    static Contact currentContact;
    Appointment selectedAppointment;


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

    public void toAppointmentDetails() throws IOException {
        //Ensure an appointment is selected.
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        //Pass the information. Found in utilities.methods.
        Stage stage1 = (Stage) (stageLabel).getScene().getWindow();
        Appointment currentAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        passTheAppointment(currentAppointment, stage1);
    }

    /**
     * Method to edit an existing appointment.
     */
    public void editAppointment() throws IOException {
        // Navigate to the appointment details page.
        toAppointmentDetails();
    }

    /**
     * Method to delete an appointment.
     */
    public void deleteAppointment() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts ("no item selected");
            return;
        }
        selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        deleteAppointmentFromAll(selectedAppointment, null,null, currentContact);
        AppointmentTable.refresh();
        AppointmentTable.getSelectionModel().clearSelection();
    }

    /**
     * Method to add an appointment.
     */
    public void addAppointment() throws IOException {
        Stage stage1 = (Stage) stageLabel.getScene().getWindow();
        passTheContactToAptDetails(currentContact, stage1);
    }

    /**
     * Create an appointment table.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> date;
    public TableColumn<Object, Object> startTime;
    public TableColumn<Object, Object> endTime;


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

        //populate the associated appointments list.
        if(!contact.getContactAppointments().isEmpty()){
            contact.getContactAppointments().clear();
        }
        for (Appointment apt: AllAppointments){
            if(currentContact.getContactID() == apt.getContactID()){
                currentContact.getContactAppointments().add(apt);
            }
        }
        //populate the appointment table.
        AppointmentTable.setItems(currentContact.getContactAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        date.setCellValueFactory(new PropertyValueFactory<>("parsedStartDate"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("parsedStartTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("parsedEndTime"));
    }

}
