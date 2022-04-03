package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.User.deleteUserAppointment;
import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class ManageProfile implements Initializable {
    public Label stageLabel;
    public TextField userIDTxt;
    public TextField userNameTxt;
    public TextField passwordTxt;
    public TextField createDateTxt;
    public TextField createdByTxt;
    public TextField lastUpdatedTxt;
    public TextField lastUpdatedByTxt;
    public TextField appointmentFilter;

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
    }       //FIXME pass the User football

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

    //Methods
    /**
     * Navigate to the appointment details page.  Pass all appointment information.
     */
    public void toAppointmentDetails() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }
        //FIXME pass the appointment football
    }

    /**
     *Navigate to the appointment details screen.  Pass the user information
     */
    public void addAppointment() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Appointment Details.fxml");
    }  //FIXME pass the User football

    /**
     * Navigate to the appointment details page.  Pass all appointment information.
     */
    public void editAppointment() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }
        //FIXME pass the appointment football
    }

    /**
     * Method to delete an appointment from the user's schedule.
     */
    public void deleteAppointment() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }
        Appointment appointment = AppointmentTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete " + appointment.getTitle() + " from the appointment list.");
        alert.setTitle("Delete this Item? ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                deleteUserAppointment(appointment);
            }
        });
    }

    /**
     * Set up the table for associated appointments.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> customerName;
    public TableColumn<Object, Object>  startTime;
    public TableColumn<Object, Object> endTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get active user data and populate text fields
        userIDTxt.setText(String.valueOf(getActiveUser(null).getUserID()));
        passwordTxt.setText(getActiveUser(null).getPassword());
        userNameTxt.setText(getActiveUser(null).getUserName());
        createDateTxt.setText(String.valueOf(getActiveUser(null).getCreateDate()));
        createdByTxt.setText(getActiveUser(null).getCreatedBy());
        lastUpdatedTxt.setText(String.valueOf(getActiveUser(null).getLastUpdate()));
        lastUpdatedByTxt.setText(getActiveUser(null).getLastUpdatedBy());
    }



}
