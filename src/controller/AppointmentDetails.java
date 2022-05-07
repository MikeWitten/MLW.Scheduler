package controller;

import DAO.DBAppointment;
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
    public ArrayList<ZonedDateTime> endTimeHours = new ArrayList<>();
    public ObservableList<String> comboBoxHours = FXCollections.observableArrayList();
    public ObservableList<String> endBoxHours = FXCollections.observableArrayList();
    public ZonedDateTime startTime;
    public ZonedDateTime endTime;
    public Appointment currentAppointment;
    public User currentUser;
    public Customer tempCustomer;
    public User tempUser;
    public Contact tempContact;

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
    public void toCustomerDetails() throws IOException, SQLException {
        if (customerCombo.getSelectionModel().isEmpty()) {
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Customer currentCustomer = customerCombo.getSelectionModel().getSelectedItem();
        passTheCustomer(currentCustomer, stage);
    }
    public void toUserDetails() throws IOException {
        if (userCombo.getSelectionModel().isEmpty()) {
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        currentUser = userCombo.getSelectionModel().getSelectedItem();
        passTheUserToUser(currentUser, stage);
    }
    public void toContactDetails() throws IOException {
        if (contactCombo.getSelectionModel().isEmpty()) {
            return;
        }
        Contact currentContact;
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        currentContact = contactCombo.getSelectionModel().getSelectedItem();
        passTheContact(currentContact, stage);
    }

    //Create a formatter for readability in the appointment form.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");

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
    }

    /**
     * Update the displayed time at company office for better user experience.
     */
    public void changeStartTimeLabel() {
        //Ensure the end time combo box is clear so that the user won't break the time continuum.
        endTimeComboBox.setItems(null);
        endTimeHours.clear();
        endBoxHours.clear();
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
        //Populate the end time list and combo box.
        int i = startTimeComboBox.getSelectionModel().getSelectedIndex();
        ZonedDateTime selectedStartTime = hereBusinessHours.get(i);
        for (ZonedDateTime zdt : hereBusinessHours) {
            if (zdt.isAfter(selectedStartTime)) {
                endTimeHours.add(zdt);
                endBoxHours.add(formatter.format(zdt));
            }
        }
        endTimeComboBox.setItems(endBoxHours);

        //Set the start Label.
        startTimeAtHQLabel.setText(formatter.format(hereBusinessHours.get(i).withZoneSameInstant(ZoneId.of("US/Eastern"))));
        startTime = hereBusinessHours.get(i);
    }
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
        //Set the end time label.
        int i = endTimeComboBox.getSelectionModel().getSelectedIndex();
        endTimeAtHQLabel.setText(formatter.format(endTimeHours.get(i).withZoneSameInstant(ZoneId.of("US/Eastern"))));
        endTime = endTimeHours.get(i);
    }

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
        for (Contact c : AllContacts) {
            if (currentAppointment.getContactID() == c.getContactID()) {
                tempContact = c;
            }
        }
        for (User u : AllUsers) {
            if (currentAppointment.getUserID() == u.getUserID()) {
                tempUser = u;
            }
        }
        for (Customer c : AllCustomers) {
            if (currentAppointment.getCustomerID() == c.getCustomerID()) {
                tempCustomer = c;
            }
        }
        //Set fields with current appointment data.
        appointmentIDTxt.setText(String.valueOf(currentAppointment.getAppointmentID()));
        titleTxt.setText(currentAppointment.getTitle());
        locationTxt.setText(currentAppointment.getLocation());
        typeTxt.setText(currentAppointment.getType());
        aptDatePicker.setValue(currentAppointment.getRawStart().toLocalDate());
        createDateTxt.setText(String.valueOf(currentAppointment.getCreateDate()));
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
     * Receive the customer info from the previous page.
     */
    public void receiveCustomer(Customer customer) {
        customerCombo.setValue(customer);
        tempCustomer = customer;
    }

    /**
     * Receive the contact info from the previous screen.
     */
    public void receiveContact(Contact contact) {
        contactCombo.setValue(contact);
        tempContact = contact;
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
        if (appointmentIDTxt.getText().isEmpty()) {
            Alerts("This information hasn't been saved yet.");
            return;
        }
        deleteAppointmentFromAll(currentAppointment, tempUser, tempCustomer, tempContact);
        toAppointmentManager();
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
        if (customerCombo.getSelectionModel().isEmpty() || userCombo.getSelectionModel().isEmpty() || contactCombo.getSelectionModel().isEmpty()) {
            Alerts("Select participants");
            return;
        }
        //Check for input that doesn't meet the 50-character database limit. Method in utilities.methods.
        if (stringTooLong(titleTxt.getText(), locationTxt.getText(), typeTxt.getText(), descriptionTxt.getText())) {
            return;
        }
        if (currentUser == null) {
            for (User user : AllUsers) {
                if (user.getUserID() == ActiveUser.getActiveUser(null).getUserID()) {
                    currentUser = user;
                }
            }
        }
        //Check for overlapping appointments.
        if(tempCustomer == null) {
            tempCustomer = customerCombo.getSelectionModel().getSelectedItem();
        }
        if(tempUser == null) {
            tempUser = userCombo.getSelectionModel().getSelectedItem();
        }
        if(tempContact == null) {
            tempContact = contactCombo.getSelectionModel().getSelectedItem();
        }
        int appointmentID;
        LocalDateTime createDate;
        String createdBy;
        if (appointmentIDTxt.getText().isEmpty()) {
            appointmentID = 90909;
            createDate = LocalDateTime.now();
            createdBy = currentUser.getUserName();
        } else {
            appointmentID = currentAppointment.getAppointmentID();
            createdBy = createdByTxt.getText();
            createDate = LocalDateTime.parse(createDateTxt.getText());
        }
        //Method found in utilities.methods.
        populateAssociatedLists(tempCustomer, tempUser, tempContact);
        //Compare appointment times.
        LocalDateTime aptStart = LocalDateTime.from(startTime);
        System.out.println(aptStart);
        LocalDateTime aptEnd = LocalDateTime.from(endTime);
        //check for overlap for customers.
        boolean isOverlapping = false;
        for (Appointment apt : tempCustomer.getAllCustomerAppointments()) {
            if (
                    (aptStart.isBefore(apt.getRawStart()) && (aptEnd.isAfter(apt.getRawEnd())) && (apt.getAppointmentID() != appointmentID)) ||
                    (aptStart.isAfter(apt.getRawStart()) && (aptStart.isBefore(apt.getRawEnd())) && (apt.getAppointmentID() != appointmentID)) ||
                    (aptEnd.isAfter(apt.getRawStart()) && aptEnd.isBefore(apt.getRawEnd()) && (apt.getAppointmentID() != appointmentID)) ||
                    (aptStart.equals(apt.getRawEnd()) || aptStart.equals(apt.getRawStart()) && (apt.getAppointmentID() != appointmentID)) ||
                    (aptEnd.equals(apt.getRawStart()) || aptEnd.equals(apt.getRawEnd()) && (apt.getAppointmentID() != appointmentID))
            ) {
                isOverlapping = true;
            }
        }
        if (isOverlapping) {
            Alerts("Customer overlapping appointment");
            return;
        }
        //Assign values to non-editable fields.
        Timestamp lastUpdated;
        String lastUpdatedBy;
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
        int customerID = tempCustomer.getCustomerID();
        int userID = tempUser.getUserID();
        int contactID = tempContact.getContactID();
        //Create the new appointment object.
        Appointment apt = new Appointment(appointmentID, title, description, location, type, aptStart,
                parsedStartDate, parsedStartTime, aptEnd, parsedEndDate, parsedEndTime, createDate, createdBy,
                lastUpdated, lastUpdatedBy, customerID, userID, contactID);
        //Determine weather to insert or update the appointment.
        if(currentAppointment == null) {
            addAppointmentToDB(apt);
            toAppointmentManager();
        } else {
            DBAppointment.updateApt(apt);
            toAppointmentManager();
        }
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
