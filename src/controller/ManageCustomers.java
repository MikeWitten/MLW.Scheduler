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

public class ManageCustomers implements Initializable {
    public Label stageLabel;
    public TextField CustomerFilterTxt;

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

    public void addCustomer() {
    } //FIXME

    public void editCustomer() {
    } //FIXME

    public void toCustomerDetails() {
    } //FIXME

    public void deleteCustomer() {
    } //FIXME

    /**
     * Create the Customer table.
     */
    public TableView<Customer> CustomerTable;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> customerName;
    public TableColumn<Object, Object> phone;
    public TableColumn<Object, Object> divisionID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
