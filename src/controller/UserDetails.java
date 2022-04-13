package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class UserDetails implements Initializable {
    public Label stageLabel;
    public ButtonBar buttonBar;
    public Button editableButton;
    public TextField userIDTxt;
    public TextField userNameTxt;
    public TextField createDateTxt;
    public TextField createdByTxt;
    public TextField lastUpdatedTxt;
    public TextField lastUpdatedByTxt;
    public TextField appointmentFilter;
    static User currentUser;

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

    public void toAppointmentDetails() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }
    }//FIXME pass the appointment football

    public void addAppointment() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Appointment Details.fxml");
    }  //FIXME pass the User football

    public void editAppointment() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }

    }//FIXME pass the appointment football

    /**
     * Get user data and populate text fields
     */
    public void receiveTheUser(User user){
        currentUser = user;
        userIDTxt.setText(String.valueOf(currentUser.getUserID()));
        userNameTxt.setText(currentUser.getUserName());
        createDateTxt.setText(String.valueOf(currentUser.getCreateDate()));
        createdByTxt.setText(currentUser.getCreatedBy());
        lastUpdatedTxt.setText(String.valueOf(currentUser.getLastUpdate()));
        lastUpdatedByTxt.setText(currentUser.getLastUpdatedBy());
    }


    //Methods.
    /**
     * Method to delete an appointment from the user's schedule.
     */
    public void deleteAppointment() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }
        Appointment appointment = AppointmentTable.getSelectionModel().getSelectedItem();
        //Method found in utilities.methods.
        deleteAppointmentFromAll(appointment, null, null, null);
        Alerts("Deleted appointment");
    }

    /**
     * Method to allow the active user to manage appointments for other users.
     */
    public void turnOnButtonBar() {
       // FIXME if(AppointmentTable.getSelectionModel().getSelectedItem().getUserID() != activeUser.getID()){}
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit another user's appointments?");
        alert.setContentText("This is not your profile. \n" +
                "Do you wish to make changes to another user's schedule?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                buttonBar.setVisible(true);
                editableButton.setVisible(false);
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

    }


}
