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
import utilities.ActiveUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public ComboBox<Contact> contactCombo;
    public ComboBox<User> userCombo;
    public ComboBox<Customer> customerCombo;
    public Button makeChanges;
    public Button cancelButton;
    public Button saveButton;
    public Button clearButton;
    public Button deleteButton;
    public Label startTimeAtHQLabel;
    public Label endTimeAtHQLabel;
    public ComboBox<String> startTimeComboBox;
    public ComboBox<String> endTimeComboBox;
    public ArrayList<ZonedDateTime> hereBusinessHours = new ArrayList<>();
    public ObservableList<String> comboBoxHours = FXCollections.observableArrayList();

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
        if(customerCombo.getSelectionModel().isEmpty()){
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Customer currentCustomer = customerCombo.getSelectionModel().getSelectedItem();
        passTheCustomer(currentCustomer, stage);
    }

    public void toUserDetails() throws IOException {
        if(userCombo.getSelectionModel().isEmpty()){
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        tempUser = userCombo.getSelectionModel().getSelectedItem();
        passTheUserToUser(tempUser, stage);
    }

    public void toContactDetails() throws IOException {
        if (contactCombo.getSelectionModel().isEmpty()){
            return;
        }
        Contact currentContact;
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        currentContact = contactCombo.getSelectionModel().getSelectedItem();
        passTheContact(currentContact, stage);
    }


    //Create a formatter for readability in the appointment form.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd 'at' hh:mm a");

    /**
     * clear the form of all data.
     */
    public void clearForm() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Appointment Details.fxml");
    }

    /**
     * Return to the form without saving changes.
     */
    public void cancelChanges() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        //Reload the page with the unchanged appointment details.
        if (currentAppointment != null) {
            passTheAppointment(currentAppointment, stage);
        }//Reload a blank form.
        else
            navigation(stage, "/view/Appointment Details.fxml");
    }

    /**
     * Method to populate combo boxes.
     */
    public void populateBoxes() {
        //Clear previous entries in the arrayList.
        hereBusinessHours.clear();
        comboBoxHours.clear();

        //Remove existing conversion display for times at company HQ.
        startTimeAtHQLabel.setText(" ");
        endTimeAtHQLabel.setText(" ");
        //Define date at user's location.
        ZoneId hereZone = ZoneId.systemDefault();
        LocalTime firstMinuteHere = LocalTime.of(0, 0);
        LocalDate dateHere = aptDatePicker.getValue();
        ZonedDateTime zdtHere = ZonedDateTime.of(dateHere, firstMinuteHere, hereZone);
        //Find instances of times that the company office is open. Place them in an arrayList.
        ZoneId companyZone = ZoneId.of("US/Eastern");
        for (int i = 0; i < 96; i++) {
            if (zdtHere.withZoneSameInstant(companyZone).toLocalTime().isAfter(LocalTime.of(7, 59)) &&
                    zdtHere.withZoneSameInstant(companyZone).toLocalTime().isBefore(LocalTime.of(22, 1))) {
                if (!(zdtHere.withZoneSameInstant(companyZone).getDayOfWeek() == DayOfWeek.SATURDAY) &&
                        !(zdtHere.withZoneSameInstant(companyZone).getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    hereBusinessHours.add(zdtHere);
                }
            }
            zdtHere = zdtHere.plusMinutes(15);
        }
        if (hereBusinessHours.isEmpty()) {
            Alerts("No available times.");
        }
        //Create an observable list to display available times.
        for (ZonedDateTime hereBusinessHour : hereBusinessHours) {
            comboBoxHours.add(formatter.format(hereBusinessHour));
        }
        //set combo box hours.
        startTimeComboBox.setItems(comboBoxHours);
        endTimeComboBox.setItems(comboBoxHours);
    }

    /**
     * Update the displayed time at company office for better user experience.
     */
    public void changeStartTimeLabel() {
        //If no selection is made, make the label invisible.
        if (startTimeComboBox.getSelectionModel().isEmpty()) {
            startTimeAtHQLabel.setText(" ");
            return;
        }
        //If no hours are available, make the label invisible.
        if (hereBusinessHours.isEmpty()) {
            startTimeAtHQLabel.setText(" ");
            return;
        }
        //Make sure the start time is before the end time
        int i = startTimeComboBox.getSelectionModel().getSelectedIndex();
        if (endTimeComboBox.getSelectionModel().getSelectedItem() != null) {
            int j = endTimeComboBox.getSelectionModel().getSelectedIndex();
            if (hereBusinessHours.get(j).isBefore(hereBusinessHours.get(i)) ||
                    hereBusinessHours.get(j).equals(hereBusinessHours.get(i))) {
                Alerts("The passage of time is important  lol");
                startTimeComboBox.setValue(null);
                startTimeAtHQLabel.setText(" ");
                return;
            }
        }
        //Set the start Label.
        startTimeAtHQLabel.setText(formatter.format(hereBusinessHours.get(i).withZoneSameInstant(ZoneId.of("US/Eastern"))));
    }  //FIXME where is the problem?
    public void changeEndTimeLabel() {
        //If no selection is made, make the label invisible.
        if (endTimeComboBox.getSelectionModel().isEmpty()) {
            endTimeAtHQLabel.setText(" ");
            return;
        }
        //If no hours are available, make the label invisible.
        if (hereBusinessHours.isEmpty()) {
            endTimeAtHQLabel.setText(" ");
            return;
        }
        //Ensure that the start time is before the end time.
        int i = endTimeComboBox.getSelectionModel().getSelectedIndex();
        if (startTimeComboBox.getSelectionModel().getSelectedItem() != null) {
            if (hereBusinessHours.get(startTimeComboBox.getSelectionModel().getSelectedIndex()).isAfter(hereBusinessHours.get(i)) ||
                    hereBusinessHours.get(startTimeComboBox.getSelectionModel().getSelectedIndex()).equals(hereBusinessHours.get(i))) {
                Alerts("The passage of time is important  lol");
                endTimeComboBox.setValue(null);
                endTimeAtHQLabel.setText(" ");
                return;
            }
        }
        //Set the end time label.
        endTimeAtHQLabel.setText(formatter.format(hereBusinessHours.get(i).withZoneSameInstant(ZoneId.of("US/Eastern"))));
    }   //FIXME where is the problem?

    /**
     * Method to stop users from changing form elements without using the proper methods.
     */
    public boolean isEditable() {
        //The location text field is only editable after the Add/Edit button is pressed.
        if (!locationTxt.isEditable()) {
            Alerts("make editable");
            return false;
        }
        return true;
    }

    /**
     * Check the date picker to ensure a date has been chosen.
     */
    public void checkForNullDate() {
        if (isEditable()) {
            if (aptDatePicker.getValue() == null) {
                Alerts("Please select a date.");
            }
        }
    }

    /**
     * Receive the appointment from the previous stage.
     */
    public void receiveAppointment(Appointment appointment) {
        currentAppointment = appointment;
        //Find the associated contact, user, and customers using their ID.
        for (Contact allContact : AllContacts) {
            if (currentAppointment.getContactID() == allContact.getContactID()) {
                tempContact = allContact;
            }
        }
        for (User allUser : AllUsers) {
            if (currentAppointment.getUserID() == allUser.getUserID()) {
                tempUser = allUser;
            }
        }
        for (Customer allCustomer : AllCustomers) {
            if (currentAppointment.getCustomerID() == allCustomer.getCustomerID()) {
                tempCustomer = allCustomer;
            }
        }
        //Set fields with current appointment data.
        appointmentIDTxt.setText(String.valueOf(currentAppointment.getAppointmentID()));
        titleTxt.setText(currentAppointment.getTitle());
        locationTxt.setText(currentAppointment.getLocation());
        typeTxt.setText(currentAppointment.getType());
        aptDatePicker.setValue(currentAppointment.getRawStart().toLocalDate());
        createDateTxt.setText(formatter.format(currentAppointment.getCreateDate()));
        createdByTxt.setText(currentAppointment.getCreatedBy());
        lastUpdatedTxt.setText(formatter.format(currentAppointment.getLastUpdate().toLocalDateTime()));
        lastUpdatedByTxt.setText(currentAppointment.getLastUpdatedBy());
        customerCombo.setValue(tempCustomer);
        userCombo.setValue(tempUser);
        contactCombo.setValue(tempContact);
        descriptionTxt.setText(currentAppointment.getDescription());
        //populate combo boxes.
        populateBoxes();
        //Assign values to the combo boxes.
        startTimeComboBox.setValue(formatter.format(currentAppointment.getRawStart()));
        changeStartTimeLabel();
        endTimeComboBox.setValue(formatter.format(currentAppointment.getRawEnd()));
        changeEndTimeLabel();
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
     * Get contact info from previous stage.
     */
    public void receiveContact(Contact contact){
        tempContact = contact;
        contactCombo.setValue(contact);
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
     * Method to delete the appointment.
     */
    public void deleteAppointment() throws IOException {
        if(appointmentIDTxt.getText().isEmpty()){
            Alerts("This information hasn't been saved yet.");
        }else{
            deleteAppointmentFromAll(currentAppointment, tempUser, tempCustomer , tempContact);
            clearForm();
        }
    }

    /**
     * Save a new or changed appointment.
     */
    public void saveChanges() throws SQLException, IOException {
        //Verify all editable fields are complete and that they meet database requirements.
        //Check for null values. Method in utilities.methods.
        if (containsNullValues(titleTxt.getText(), locationTxt.getText(), typeTxt.getText(), descriptionTxt.getText())) {
            return;
        }
        if (customerCombo.getSelectionModel().isEmpty() || userCombo.getSelectionModel().isEmpty() || contactCombo.getSelectionModel().isEmpty()){
            Alerts("Select participants");
            return;
        }
        //Check for input that doesn't meet the 50-character database limit. Method in utilities.methods.
        if (stringTooLong(titleTxt.getText(), locationTxt.getText(), typeTxt.getText(), descriptionTxt.getText())) {
            return;
        }
        if(currentUser == null){
            for (User user: AllUsers) {
                if (user.getUserID() == ActiveUser.getActiveUser(null).getUserID()){
                    currentUser = user;
                }
            }
        }
        //Assign an appointment ID if it is null.
        int appointmentID;
        if(appointmentIDTxt.getText().isEmpty()){
            appointmentID = 90909;
        } else
            appointmentID = Integer.parseInt(appointmentIDTxt.getText());
        //Check for overlapping appointments.
        tempCustomer = customerCombo.getSelectionModel().getSelectedItem();
        tempUser = userCombo.getSelectionModel().getSelectedItem();
        tempContact = contactCombo.getSelectionModel().getSelectedItem();
        //Method found in utilities.methods.
        populateAssociatedLists(tempCustomer, tempUser, tempContact);
        //Compare appointment times.
        LocalDateTime aptStart = hereBusinessHours.get(startTimeComboBox.getSelectionModel().getSelectedIndex()).toLocalDateTime();
        System.out.println(aptStart);
        LocalDateTime aptEnd = hereBusinessHours.get(endTimeComboBox.getSelectionModel().getSelectedIndex()).toLocalDateTime();
        //check for overlap for customers.
        boolean isOverlapping = false;
        for (Appointment apt: tempCustomer.getAllCustomerAppointments()){
            if     ((aptStart.isAfter(apt.getRawStart()) && aptStart.isBefore(apt.getRawEnd()) && apt.getAppointmentID() != appointmentID) ||
                    (aptEnd.isBefore(apt.getRawEnd()) && aptEnd.isAfter(apt.getRawStart()) && apt.getAppointmentID() != appointmentID) ||
                    (aptStart.equals(apt.getRawStart()) && apt.getAppointmentID() != appointmentID) ||
                    (aptStart.equals(apt.getRawEnd()) && apt.getAppointmentID() != appointmentID) ||
                    (aptEnd.equals(apt.getRawStart()) && apt.getAppointmentID() != appointmentID) ||
                    (aptEnd.equals(apt.getRawEnd()) && apt.getAppointmentID() != appointmentID)){
                isOverlapping = true;
            }
        }
        if (isOverlapping){
            Alerts("Customer overlapping appointment");
            return;
        }
        //Assign values to non-editable fields.
        LocalDateTime createDate;
        String createdBy;
        Timestamp lastUpdated;
        String lastUpdatedBy;
        if(appointmentIDTxt.getText().isEmpty()){
            appointmentID = 90909;
        } else
            appointmentID = Integer.parseInt(appointmentIDTxt.getText());
        if(createDateTxt.getText().isEmpty()){
            createDate = LocalDateTime.now();
        }else
            createDate = currentAppointment.getCreateDate();
        if(createdByTxt.getText().isEmpty()){
            createdBy = currentUser.getUserName();
        }else createdBy = createdByTxt.getText();
        lastUpdated = Timestamp.from(Instant.now());
        lastUpdatedBy = currentUser.getUserName();
        //Assign user defined attributes.
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDate parsedStartDate = aptStart.toLocalDate();
        LocalDate parsedEndDate = aptEnd.toLocalDate();
        LocalTime parsedStartTime = aptStart.toLocalTime();
        LocalTime parsedEndTime = aptEnd.toLocalTime();
        int customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomerID();
        int userID = userCombo.getSelectionModel().getSelectedItem().getUserID();
        int contactID = contactCombo.getSelectionModel().getSelectedItem().getContactID();

        Appointment newAppointment = new Appointment(appointmentID, title, description, location, type, aptStart,
                parsedStartDate, parsedStartTime, aptEnd, parsedEndDate, parsedEndTime, createDate, createdBy,
                lastUpdated, lastUpdatedBy, customerID, userID, contactID);

        addAppointmentToDB(newAppointment);
        clearForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Explain non-editable fields.
        appointmentIDTxt.setTooltip(tooltip);
        createDateTxt.setTooltip(tooltip);
        createdByTxt.setTooltip(tooltip);
        lastUpdatedTxt.setTooltip(tooltip);
        lastUpdatedByTxt.setTooltip(tooltip);

        //Populate the combo boxes.
        userCombo.setItems(AllUsers);
        contactCombo.setItems(AllContacts);
        customerCombo.setItems(AllCustomers);

        //Make labels invisible until times are chosen.
        if (startTimeComboBox.getSelectionModel().getSelectedItem() == null) {
            startTimeAtHQLabel.setText(" ");
        }
        if (endTimeComboBox.getSelectionModel().getSelectedItem() == null) {
            endTimeAtHQLabel.setText(" ");
        }
    }

}
