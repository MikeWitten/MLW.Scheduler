package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import static utilities.Methods.*;

public class FirstLevelDivision{
    public Label stageLabel;
    public Button countryButton;
    public Label createDateLabel;
    public Label createdByLabel;
    public Label divisionLabel;
    public Label divisionIDLabel;
    public Label lastUpdateLabel;
    public Label updateByLabel;
    public TableView <Customer> CustomerTable;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> customerName;
    public TableColumn<Object, Object> phone;
    public Division currentDivision;
    public Country currentCountry;


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
    } //FIXME

    /**
     * Create a formatter for Readability.
     */
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd 'of' yyyy  'at' HH:mm a");

    /**
     * Method to receive the division object from the previous screen.
     */
    public void receiveDivision(Division division) {
        currentDivision = division;
        //Identify the associated country
        for(Country country: AllCountries){
            if(country.getCountryID() == currentDivision.getCountryID()){
                currentCountry = country;
                break;
            }
        }
        //Ensure associated list is up-to-date.
        currentDivision.getDivisionCustomerList().clear();
        for(Customer c: AllCustomers){
            if(currentDivision.getDivisionID() == c.getDivisionID()){
                currentDivision.getDivisionCustomerList().add(c);
            }
        }
        //Populate the associated customers table.
        CustomerTable.setItems(currentDivision.getDivisionCustomerList());
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        //Set labels and button.
        countryButton.setText(currentCountry.getCountry());
        createDateLabel.setText(formatter.format(currentDivision.getCreateDate()));
        createdByLabel.setText(currentCountry.getCreatedBy());
        divisionLabel.setText(currentDivision.getDivision());
        divisionIDLabel.setText(String.valueOf(currentDivision.getDivisionID()));
        lastUpdateLabel.setText(formatter.format(currentDivision.getLastUpdate().toLocalDateTime()));
        updateByLabel.setText(currentCountry.getLastUpdatedBy());
    }

    /**
     * Method to add a customer to the division.
     */
    public void addCustomer() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        sendCustomerToEdit(null,stage);
    }

    /**
     * Method to edit an existing customer.
     */
    public void editCustomer() throws IOException {
        if(CustomerTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Customer customer = CustomerTable.getSelectionModel().getSelectedItem();
        sendCustomerToEdit(customer, stage);
    }

    /**
     * Method to delete a customer from the division list.
     */
    public void deleteCustomer() {
        if(CustomerTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Customer customer = CustomerTable.getSelectionModel().getSelectedItem();
        deleteCustomerFromAll(customer);
        CustomerTable.getSelectionModel().clearSelection();
        CustomerTable.refresh();
    }

    /**
     * Method to view the details of a customer in the list.
     */
    public void toCustomerDetails() throws IOException, SQLException {
        if(CustomerTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Customer customer = CustomerTable.getSelectionModel().getSelectedItem();
        passTheCustomer(customer, stage);
    }



}
