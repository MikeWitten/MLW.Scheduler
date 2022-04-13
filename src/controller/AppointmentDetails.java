package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class AppointmentDetails implements Initializable {
    public Label stageLabel;
    public TextField appointmentIDTxt;
    public TextField titleTxt;
    public TextField locationTxt;
    public TextField typeTxt;
    public TextField createDateTxt;
    final Tooltip tooltip = new Tooltip("This field is AutoPopulated.");      //explains text fields.
    public TextField createdByTxt;
    public TextField lastUpdatedTxt;
    public TextField lastUpdatedByTxt;
    public TextField customerIDTxt;
    public TextField userIDTxt;
    public TextField contactIDTxt;
    public TextArea descriptionTxt;
    public DatePicker aptDatePicker;
    public ComboBox<LocalTime> startTime;
    public ComboBox<LocalTime> endTime;
    public Button makeChanges;
    public Button cancelButton;
    public Button saveButton;
    public Button clearButton;
    Appointment currentAppointment;
    User currentUser;

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
        //Find the customer
        Customer currentCustomer = null;
        for (Customer allCustomer : AllCustomers) {
            if (allCustomer.getCustomerID() == Integer.parseInt(customerIDTxt.getText())) {
                currentCustomer = allCustomer;
            }
        }
        passTheCustomer(currentCustomer, stage);
    }

    public void toUserDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        for (User allUser : AllUsers) {
            if (allUser.getUserID() == Integer.parseInt(userIDTxt.getText())) {
                currentUser = allUser;
            }
            passTheUserToUser(currentUser, stage);
        }
    }

    public void toContactDetails() throws IOException {
        Contact currentContact = null;
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        for (Contact allContact : AllContacts) {
            if (allContact.getContactID() == Integer.parseInt(contactIDTxt.getText())) {
                currentContact = allContact;
            }
        }
        passTheContact(currentContact, stage);

    } //FIXME pass the contact football


    //Create a formatter for readability in the appointment form.
    DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("MMM dd yyyy   HH:mm");

    /**
     * Create an observable list to populate the combo boxes on the appointment form.
     */
    ObservableList<LocalTime> availableTimes = FXCollections.observableArrayList();

    /**
     * Method to stop users from changing form elements without using the proper methods.
     */
    public void checkForEditable() {
        //The location text field is only editable after the Add/Edit button is pressed.
        if (!locationTxt.isEditable()){
            Alerts("make editable");
        }
    }

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
        aptDatePicker.setValue(currentAppointment.getStart().toLocalDate());
        startTime.setItems(availableTimes);
        startTime.setValue(currentAppointment.getStart().toLocalTime());
        endTime.setItems(availableTimes);
        endTime.setValue(currentAppointment.getEnd().toLocalTime());
        createDateTxt.setText(timeFormatter.format(currentAppointment.getCreateDate()));
        createdByTxt.setText(currentAppointment.getCreatedBy());
        lastUpdatedTxt.setText(timeFormatter.format(currentAppointment.getLastUpdate().toLocalDateTime()));
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
        currentUser = user;
        lastUpdatedByTxt.setText(currentUser.getUserName());
        lastUpdatedTxt.setText(String.valueOf(Timestamp.from(Instant.now())));
        createdByTxt.setText(currentUser.getUserName());
        createDateTxt.setText(String.valueOf(LocalDateTime.now()));
    }

    /**
     * Make fields editable for creating new appointments or changing existing ones.
     */
    public void makeEditable() {
        titleTxt.setEditable(true);
        locationTxt.setEditable(true);
        typeTxt.setEditable(true);
        descriptionTxt.setEditable(true);
        customerIDTxt.setEditable(true);
        userIDTxt.setEditable(true);
        contactIDTxt.setEditable(true);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        clearButton.setVisible(false);
        makeChanges.setVisible(false);

    }

    /**
     * Save a new or changed appointment.
     */
    public void saveChanges() {
    } //FIXME

    /**
     * Return to the form without saving changes.
     */
    public void cancelChanges() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        //Reload the page with the unchanged appointment details.
        if(currentAppointment != null){
            passTheAppointment(currentAppointment, stage);
        } //Reload the page with the current user details.
        else if (currentUser != null) {
            passTheUser(currentUser, stage);
        }//Reload a blank form.
        else
            navigation(stage, "/view/Appointment Details.fxml");
    }

    /**
     * clear the form of all data.
     */
    public void clearForm() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Appointment Details.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Explain non-editable fields.
        appointmentIDTxt.setTooltip(tooltip);
        createDateTxt.setTooltip(tooltip);
        createdByTxt.setTooltip(tooltip);
        lastUpdatedTxt.setTooltip(tooltip);
        lastUpdatedByTxt.setTooltip(tooltip);

        //Determine the time difference between the user's local system and the company headquarters.
        LocalTime timeHere= LocalDateTime.now().toLocalTime();
        LocalTime timeAtHQ = LocalDateTime.now(ZoneId.of("US/Eastern")).toLocalTime();
        int timeDifference = timeHere.getHour() - timeAtHQ.getHour();

        //Create a for loop to populate the available times in the combo boxes.
        for (int i = 8; i < 22; i++) {
            for (int j = 0; j < 60; j++) {
                availableTimes.add(LocalTime.of(i + timeDifference, j));
            }
            availableTimes.add(LocalTime.of(22 + timeDifference, 0));
        }
    }


}
