package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class AppointmentDetails implements Initializable {
    public Label stageLabel;
    public TextField appointmentIDTxt;
    public TextField titleTxt;
    public TextField locationTxt;
    public TextField typeTxt;
    public TextField createDateTxt;
    public TextField createdByTxt;
    public TextField lastUpdatedTxt;
    public TextField lastUpdatedByTxt;
    public TextField customerIDTxt;
    public TextField userIDTxt;
    public TextField contactIDTxt;
    public TextArea descriptionTxt;
    public DatePicker aptDatePicker;
    public ComboBox<Integer> startHourCombo;
    public ComboBox<Integer> startMinuteCombo;
    public ComboBox<Integer> endHourCombo;
    public ComboBox<Integer> endMinuteCombo;
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
        aptDatePicker.setValue(currentAppointment.getStart().toLocalDate());
        startHourCombo.getSelectionModel().select(currentAppointment.getStart().getHour());
        startMinuteCombo.getSelectionModel().select((Integer) currentAppointment.getStart().getMinute());
        endHourCombo.getSelectionModel().select(currentAppointment.getEnd().getHour());
        endMinuteCombo.getSelectionModel().select((Integer) currentAppointment.getEnd().getMinute());
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
     * Make fields editable for creating new appointments or changing existing ones.
     */
    public void makeEditable() {
    } //FIXME

    /**
     * Save a new or changed appointment.
     */
    public void saveChanges() {
    } //FIXME

    /**
     * clear the form of all imported data.
     */
    public void clearForm() {
    } //FIXME


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Determine the current time zone and compare it to the HQ timezone.
        int timeHere = LocalDateTime.now().getHour();
        int timeInNY = LocalDateTime.now(ZoneId.of("US/Eastern")).getHour();
        int timeDifference = timeHere - timeInNY;

        //Create Lists to populate the combo boxes.
        ObservableList<Integer> startHoursList = FXCollections.observableArrayList();
        ObservableList<Integer> endHoursList = FXCollections.observableArrayList();
        ObservableList<Integer> minutesList = FXCollections.observableArrayList();

        //Populate the hours box starting at the eastern timezone 8am and going until 1 hour before end of business.
        for (int i = 0; i < 14; i++) {
            startHoursList.add(i + 8 + timeDifference);
        }

        //Populate the end hours list to include the last business hour in the eastern timezone.
        for (int i = 0; i < 15; i++) {
            endHoursList.add(i + 8 + timeDifference);
        }

        //Populate the minutes box with 0 - 59.
        for (int i = 0; i < 60; i++) {
                minutesList.add(i);
        }

        startHourCombo.setItems(startHoursList);
        startMinuteCombo.setItems(minutesList);
        endHourCombo.setItems(endHoursList);
        endMinuteCombo.setItems(minutesList);


    }
}
