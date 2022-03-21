package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class ManageAppointments implements Initializable {
    public Label stageLabel;

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

    //Methods

    /**
     * Method to add appointment.
     */
    public void addAppointment() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Appointment Details.fxml");
    }

    /**
     * Method to edit an existing appointment.
     */
    public void editAppointment(){
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
    }  //FIXME pass the Appointment football

    /**
     * Method to view existing appointment details.
     */
    public void toAppointmentDetails() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
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

    }


    /**
     * Method to delete appointment.
     */
    public void deleteAppointment(ActionEvent actionEvent) {
    }
}
