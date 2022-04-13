package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class CustomerDetails implements Initializable {
    public Label stageLabel;
    public TextField customerIDTxt;
    public TextField customerNameTxt;
    public TextField streetAddressTxt;
    public ComboBox<model.Country> countryComboBox;
    public ComboBox<model.Division> stateComboBox;
    public TextField postalCodeTxt;
    public TextField phoneTxt;
    public TextField createdOnTxt;
    public TextField createdByTxt;
    public TextField lastUpdateTxt;
    public TextField lastUpdatedByTxt;
    public TextField divisionIDTxt;
    public TextField appointmentFilter;
    static Customer currentCustomer;
    static Division currentDivision;
    static Country currentCountry;

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

    public void toCountryDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Country.fxml");
    }  //FIXME pass the Country football

    public void toDivisionDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/First Level Division.fxml");
    }  //FIXME pass the division football

    public void toAppointmentDetails() {
    } //FIXME pass appointment football

    /**
     * Method to add an appointment
     */
    public void addAppointment() {
    } //FIXME

    /**
     * Method to edit an existing appointment.
     */
    public void editAppointment() {
    } //FIXME

    /**
     * Method to delete an appointment.
     */
    public void deleteAppointment() {
    } //FIXME

    /**
     * Receive the customer object from the previous scene.
     */
    public void receiveCustomer(Customer customer){
        //Assign value to the current customer and check to ensure the associated appointment list is up-to-date.
        currentCustomer = customer;
       /* if (currentCustomer.getAllCustomerAppointments() != null){
            currentCustomer.getAllCustomerAppointments().clear();
        }*/
        //Set text boxes.
        customerIDTxt.setText(String.valueOf(currentCustomer.getCustomerID()));
        customerNameTxt.setText(currentCustomer.getCustomerName());
        streetAddressTxt.setText(currentCustomer.getAddress());
        postalCodeTxt.setText(currentCustomer.getPostalCode());
        phoneTxt.setText(currentCustomer.getPhone());
        createdOnTxt.setText(String.valueOf(currentCustomer.getCreateDate()));
        createdByTxt.setText(currentCustomer.getCreatedBy());
        lastUpdateTxt.setText(String.valueOf(currentCustomer.getLastUpdate()));
        lastUpdatedByTxt.setText(currentCustomer.getLastUpdatedBy());
        divisionIDTxt.setText(String.valueOf(currentCustomer.getDivisionID()));

        //Identify the division and country.
        for (Division allDivision : AllDivisions) {
            if (allDivision.getDivisionID() == currentCustomer.getDivisionID()) {
                currentDivision = allDivision;
            }
        }
        for (Country allCountry : AllCountries) {
            if (allCountry.getCountryID() == currentDivision.getCountryID()) {
                currentCountry = allCountry;
            }

        }

        //Set the combo boxes default values.
        countryComboBox.setValue(currentCountry);
        stateComboBox.setValue(currentDivision);
    }

    /**
     * Create the appointment table.
     */
    public TableView <Appointment> AppointmentTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> customerName;
    public TableColumn<Object, Object> startTime;
    public TableColumn<Object, Object> endTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set combo boxes up.
        countryComboBox.setItems(AllCountries);
        stateComboBox.setItems(AllDivisions);
    }
}
