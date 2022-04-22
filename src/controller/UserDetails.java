package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public void toAppointmentDetails() throws IOException {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Appointment a = AppointmentTable.getSelectionModel().getSelectedItem();
        passTheAppointment(a, stage);
    }

    public void addAppointment() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        passTheUser(currentUser, stage);
    }
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
        //Ensure associated appointment list is up to date.
        currentUser.getUserAppointments().clear();
        for (Appointment a: AllAppointments){
            if(currentUser.getUserID() == a.getUserID()){
                currentUser.getUserAppointments().add(a);
            }
        }
        //populate the table.
        AppointmentTable.setItems(currentUser.getUserAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        date.setCellValueFactory(new PropertyValueFactory<>("parsedStartDate"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("parsedStartTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("parsedEndTime"));
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
        AppointmentTable.getSelectionModel().clearSelection();
    }

    /**
     * Set up the table for associated appointments.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> date;
    public TableColumn<Object, Object>  startTime;
    public TableColumn<Object, Object> endTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
