package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class ManageAppointments implements Initializable {
    public Label stageLabel;
    public Label monthOfTheYear;
    public TextField appointmentFilterTxt;
    public RadioButton monthViewButton;
    public RadioButton weekViewButton;
    public Button prevMonthButton;
    public Button nextMonthButton;
    public Button prevWeekButton;
    public Button nextWeekButton;

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
    }       //FIXME pass the User Football

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

    /**
     * Navigate to add appointment.
     */
    public void addAppointment() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Appointment Details.fxml");
    }

    /**
     * Navigate to edit an existing appointment.
     */
    public void editAppointment(){
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
    }  //FIXME pass the Appointment football

    /**
     * Navigate to view existing appointment details.
     */
    public void toAppointmentDetails() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
    }

    /**
     * Method to delete appointment.
     */
    public void deleteAppointment() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Appointment appointment = AppointmentTable.getSelectionModel().getSelectedItem();
        deleteAppointmentFromAll(appointment, null, null, null);
        Alerts("Deleted appointment");
        AppointmentTable.refresh();
    }

    /**
     * Method to change the table to filter appointments by month.
     */
    public void toMonthView() {
        monthViewButton.setSelected(true);
        monthViewButton.setDisable(true);
        nextMonthButton.setVisible(true);
        prevMonthButton.setVisible(true);
        nextWeekButton.setVisible(false);
        prevWeekButton.setVisible(false);
        weekViewButton.setDisable(false);
        weekViewButton.setSelected(false);
        //FIXME change to month view.
    }

    /**
     * Method to change the table to filter by month.
     */
    public void toWeekView() {
        monthViewButton.setSelected(false);
        monthViewButton.setDisable(false);
        nextMonthButton.setVisible(false);
        prevMonthButton.setVisible(false);
        nextWeekButton.setVisible(true);
        prevWeekButton.setVisible(true);
        weekViewButton.setDisable(true);
        weekViewButton.setSelected(true);
        //FIXME change to week view.
    }

    /**
     * Method to filter results to the next month.
     */
    public void previousMonthFilter() {
    }

    /**
     * Method to filter results to the next month.
     */
    public void nextMonthFilter(ActionEvent actionEvent) {
    }

    /**
     * Method to filter to previous week.
     */
    public void previousWeekFilter() {
    }

    /**
     * Method to filter to next week.
     */
    public void nextWeekFilter() {
    }



    /**
     * Set up Appointment Table.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> aptIDColumn;
    public TableColumn<Object, Object> titleColumn;
    public TableColumn<Object, Object> customerColumn;
    public TableColumn<Object, Object> contactColumn;
    public TableColumn<Object, Object> locationColumn;
    public TableColumn<Object, Object> startColumn;
    public TableColumn<Object, Object> endColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set values in the table.
        AppointmentTable.setItems(getAllAppointments());
        aptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
    }



}
