package controller;

import DAO.DBCustomer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import static utilities.Methods.*;

public class CustomerDetails {
    static Customer currentCustomer;
    static Division currentDivision;
    static Country currentCountry;
    public Label stageLabel;
    public Label customerNameLabel;
    public Label customerIDLabel;
    public Label streetAddressLabel;
    public Label phoneLabel;
    public Label stateProvinceZipLabel;
    public Label countryLabel;
    public Label createdByLabel;
    public Label createDateLabel;
    public Label updatedByLabel;
    public Label lastUpdateLabel;
    public Button countryButton;
    public Button divisionButton;
    public TableView <Appointment> AppointmentTable;
    public TableColumn <Object, Object> appointmentID;
    public TableColumn <Object, Object> title;
    public TableColumn <Object, Object> date;
    public TableColumn <Object, Object> startTime;
    public TableColumn <Object, Object> endTime;

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
    public void toCountryDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        passTheCountryToCountryDetails(currentCountry, stage);
    }
    public void toDivisionDetails() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        passTheDivisionToDivision(currentDivision, stage);
    }
    public void addAppointment() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        passTheCustomerToAptDetails(currentCustomer, stage);
    }
    public void toAppointmentDetails() throws IOException {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Appointment appointment = AppointmentTable.getSelectionModel().getSelectedItem();
        passTheAppointment(appointment, stage);
    }
    public void editCustomer() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        sendCustomerToEdit(currentCustomer, stage);
    }

    /**
     * Adjust the date to be more readable.
     */
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at:' hh:mm a");

    /**
     * Receive the customer object from the previous screen.
     */
    public void receiveCustomer(Customer customer) throws SQLException {
        AllCustomers.clear();
        DBCustomer.selectAllCustomers();
        //Assign the global variable a value.
        for(Customer c: AllCustomers){
            if(customer.getCustomerName().equals(c.getCustomerName()) && customer.getPhone().equals(c.getPhone())){
                currentCustomer = c;
            }
        }
        //Find current Country and Division.
        getCountryAndDivision();
        customerNameLabel.setText(currentCustomer.getCustomerName());
        customerIDLabel.setText("ID: " + currentCustomer.getCustomerID());
        streetAddressLabel.setText(currentCustomer.getAddress());
        phoneLabel.setText(currentCustomer.getPhone());
        stateProvinceZipLabel.setText(currentDivision.getDivision() + ", " + currentCustomer.getPostalCode());
        countryLabel.setText(currentCountry.getCountry());
        createdByLabel.setText(currentCustomer.getCreatedBy());
        createDateLabel.setText(formatter.format(currentCustomer.getCreateDate()));
        updatedByLabel.setText(currentCustomer.getLastUpdatedBy());
        lastUpdateLabel.setText(formatter.format(currentCustomer.getLastUpdate().toLocalDateTime()));
        countryButton.setText(currentCountry.getCountry());
        divisionButton.setText(currentDivision.getDivision() + ", ID " + currentDivision.getDivisionID());
        //Refresh the associated appointments for the customer.
        refreshAppointments();
        //Populate the associated table.
        populateTable();
    }

    /**
     * Method to populate table.
     */
    private void populateTable() {
        AppointmentTable.setItems(currentCustomer.getAllCustomerAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        date.setCellValueFactory(new PropertyValueFactory<>("parsedStartDate"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("parsedStartTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("parsedEndTime"));
    }

    /**
     * Method to ensure that if a customer is re-loaded they will not have duplicate appointments.
     */
    private void refreshAppointments() {
        currentCustomer.getAllCustomerAppointments().clear();
        for (Appointment a : AllAppointments){
            if(currentCustomer.getCustomerID() == a.getCustomerID()){
                currentCustomer.getAllCustomerAppointments().add(a);
            }
        }
    }

    /**
     * Method to determine the country and division of the current customer.
     */
    private void getCountryAndDivision() {
        for (Division d : AllDivisions){
            if (currentCustomer.getDivisionID() == d.getDivisionID()){
                currentDivision = d;
            }
        }
        for (Country c: AllCountries){
            if(currentDivision.getCountryID() == c.getCountryID()){
                currentCountry = c;
            }
        }
    }

    /**
     * Method to delete the appointment.
     */
    public void deleteAppointment() {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        //Method found in utilities.methods.
        Appointment a = AppointmentTable.getSelectionModel().getSelectedItem();
        deleteAppointmentFromAll(a, null, currentCustomer, null);
        AppointmentTable.getSelectionModel().clearSelection();
    }

    /**
     * Method to delete a customer.
     */
    public void deleteCustomer() throws IOException {
        deleteCustomerFromAll(currentCustomer);
        toHome();
    }
}
