package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Methods.logOutHere;

public class MainScreen implements Initializable {
    public Label label1;

    /**
     * Navigate to appointment details, bringing the appointment details over to the new screen.
     */
    public void toAppointmentDetails(ActionEvent actionEvent) {
        if(AppointmentsTable.getSelectionModel().getSelectedItem() == null){
            Alerts(1); //no selected Item.
            return;
        }

    }
    public void toAppointmentManager(ActionEvent actionEvent) {
    }
    public void toCustomerDetailsView(ActionEvent actionEvent) {
    }
    public void toCustomerDetailsAdd(ActionEvent actionEvent) {
    }
    public void toExitProgram() {
        Stage stage = (Stage) label1.getScene().getWindow();
        logOutHere(stage);
    }

    /**
     * Set up the "Appointments Table".
     */
    public TableView<Appointment> AppointmentsTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> appointmentType;
    public TableColumn<Object, Object> startTime;
    public TableColumn<Object, Object> endTime;

    /**
     * Set up the "Customer Table".
     */
    public TableView<Customer> CustomerTable;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> customerName;
    public TableColumn<Object, Object> customerPhone;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
