package controller;

import DAO.DBCustomer;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    public void addCustomer() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        sendCustomerToEdit(null, stage);
    }

    public void toCustomerDetails() throws IOException, SQLException {
        if(CustomerTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Customer c = CustomerTable.getSelectionModel().getSelectedItem();
        passTheCustomer(c, stage);
    }

    public void deleteCustomer() {
        if(CustomerTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Customer c = CustomerTable.getSelectionModel().getSelectedItem();
        deleteCustomerFromAll(c);
        CustomerTable.getSelectionModel().clearSelection();
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
        //Ensure list is up-to-date.
        AllCustomers.clear();
        try {
            DBCustomer.selectAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //set table
        CustomerTable.setItems(AllCustomers);
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionID.setCellValueFactory(new PropertyValueFactory<>("divisionID"));


    }
}
