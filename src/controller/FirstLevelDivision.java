package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class FirstLevelDivision implements Initializable {
    public Label stageLabel;
    public TextField divisionIDTxt;
    public TextField divisionTxt;
    public TextField createDateTxt;
    public TextField CreatedByTxt;
    public TextField countryTxt;
    public TextField lastUpdatedTxt;
    public TextField lastUpdatedByTxt;
    public TextField customerFilterTxt;

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

    /**
     * Method to add a customer to the division.
     */
    public void addCustomer() {
    }   //FIXME

    /**
     * Method to edit an existing customer.
     */
    public void editCustomer() {
    }  //FIXME

    /**
     * Method to delete a customer from the division list.
     */
    public void deleteCustomer() {
    } //FIXME

    /**
     * Method to view the details of a customer in the list.
     */
    public void toCustomerDetails() {
    } //FIXME






    /**
     * Create customer table.
     */
    public TableView<Customer> CustomerTable;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> customerName;
    public TableColumn<Object, Object> phone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
