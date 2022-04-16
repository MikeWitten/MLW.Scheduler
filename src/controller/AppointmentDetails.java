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
    public TextArea descriptionTxt;
    public DatePicker aptDatePicker;
    public ComboBox<LocalTime> startTime;
    public ComboBox<LocalTime> endTime;
    public ComboBox<Contact> contactCombo;
    public ComboBox<User> userCombo;
    public ComboBox<Customer> customerCombo;
    public Button makeChanges;
    public Button cancelButton;
    public Button saveButton;
    public Button clearButton;
    public CheckBox overnight;
    Appointment currentAppointment;
    User currentUser;
    Customer tempCustomer;
    User tempUser;
    Contact tempContact;

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

    public void toAppointmentManager() throws IOException {
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
        Customer currentCustomer = customerCombo.getSelectionModel().getSelectedItem();
        passTheCustomer(currentCustomer, stage);
    }

    public void toUserDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        currentUser = userCombo.getSelectionModel().getSelectedItem();
        passTheUserToUser(currentUser, stage);
    }

    public void toContactDetails() throws IOException {
        Contact currentContact = null;
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        currentContact = contactCombo.getSelectionModel().getSelectedItem();
        passTheContact(currentContact, stage);
    }


    //Create a formatter for readability in the appointment form.
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy   HH:mm");

    /**
     * Create an observable list to populate the combo boxes on the appointment form.
     */
    ObservableList<LocalTime> availableTimes = FXCollections.observableArrayList();

    /**
     * Method to stop users from changing form elements without using the proper methods.
     */
    public void checkForEditable() {
        //The location text field is only editable after the Add/Edit button is pressed.
        if (!locationTxt.isEditable()) {
            Alerts("make editable");
        }
    }

    /**
     * Receive the appointment from the previous stage.
     */
    public void receiveAppointment(Appointment appointment) {
        currentAppointment = appointment;
        System.out.println("OK");
        //Find the associated contact, user, and customers using their ID.
        for (Contact allContact : AllContacts) {
            if (currentAppointment.getContactID() == allContact.getContactID()) {
                tempContact = allContact;
            }
        }

        for (User allUser : AllUsers) {
            if(currentAppointment.getUserID() == allUser.getUserID()){
                tempUser = allUser;
            }
        }

        for (Customer allCustomer: AllCustomers) {
            if (currentAppointment.getCustomerID() == allCustomer.getCustomerID()){
                tempCustomer = allCustomer;
            }
        }

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
        customerCombo.setValue(tempCustomer);
        userCombo.setValue(tempUser);
        contactCombo.setValue(tempContact);
        descriptionTxt.setText(currentAppointment.getDescription());

        //set the overnight field if it is appropriate.
        if(currentAppointment.getStart().isAfter(currentAppointment.getEnd())){
            overnight.setSelected(true);  //FIXME check your work drowsy bastard.
        }
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
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        clearButton.setVisible(false);
        makeChanges.setVisible(false);

    }

    /**
     * Save a new or changed appointment.
     */
    public void saveChanges() {
        //Verify all editable fields are complete and that they meet database requirements.
        //Check for null values. Method in utilities.methods.
        if (containsNullValues(titleTxt.getText(), locationTxt.getText(), typeTxt.getText(), descriptionTxt.getText())) {
            return;
        }

        //Check for input that doesn't meet the 50-character database limit. Method in utilities.methods.
        if (stringTooLong(titleTxt.getText(), locationTxt.getText(), typeTxt.getText(), descriptionTxt.getText())) {
            return;
        }

        //Ensure the start time is before the end time.
        if (startTime.getSelectionModel().getSelectedItem().isAfter(endTime.getSelectionModel().getSelectedItem()) ||
                startTime.getSelectionModel().getSelectedItem().equals(endTime.getSelectionModel().getSelectedItem())) {
            Alerts("The passage of time is important  lol");
            return;
        }
        if (aptDatePicker.getValue().isBefore(LocalDateTime.now().toLocalDate()) ||
                aptDatePicker.getValue().equals(LocalDateTime.now().toLocalDate()) &&
                        endTime.getSelectionModel().getSelectedItem().isBefore(LocalDateTime.now().toLocalTime())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Your time is passed! Meet your DOOM!");
            alert.setContentText("The time you have chosen for your meeting has already ended.  Would you like to continue");
            alert.setWidth(550);
            alert.setHeight(550);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    return;
                }
            });
        }  //FIXME Resume Here.



        //assign values to non-editable fields

        //send appointment to the database.
    } //FIXME


    /**
     * Return to the form without saving changes.
     */
    public void cancelChanges() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        //Reload the page with the unchanged appointment details.
        if (currentAppointment != null) {
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
        overnight.setTooltip(tooltip);      //FIXME remember to set overnight field


        //Determine the time difference between the user's local system and the company headquarters.
        LocalTime timeHere = LocalDateTime.now().toLocalTime();
        LocalTime timeAtHQ = LocalDateTime.now(ZoneId.of("US/Eastern")).toLocalTime();
        int timeDifference = timeHere.getHour() - timeAtHQ.getHour();

        //Create a for loop to populate the available times in the combo boxes.
        for (int i = 8; i < 22; i++) {
            for (int j = 0; j < 60; j++) {
                if(i + timeDifference > 24){
                    availableTimes.add(LocalTime.of(i + timeDifference - 24, j));
                }else
                availableTimes.add(LocalTime.of(i + timeDifference, j));
            }
            if (22 + timeDifference > 24){
                availableTimes.add(LocalTime.of(-2 + timeDifference, 0));
            }else
            availableTimes.add(LocalTime.of(22 + timeDifference, 0));
        }

        //Populate the combo boxes.
        userCombo.setItems(AllUsers);
        contactCombo.setItems(AllContacts);
        customerCombo.setItems(AllCustomers);
    }


}
