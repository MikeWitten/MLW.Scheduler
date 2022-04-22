package controller;

import DAO.DBCountry;
import DAO.DBCustomer;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class CustomerEdit implements Initializable {

    public Label customerNameLabel;
    public Label streetAddressLabel;
    public Label phoneLabel;
    public Label stateProvinceZipLabel;
    public Label countryLabel;
    public Label previewLabel;
    public Label startPrompt;
    public ComboBox<model.Country> countryBox;
    public Label divPrompt;
    public ComboBox<model.Division> divisionBox;
    public TextField addressTxt;
    public Button next1;
    public Button next2;
    public Button next3;
    public Customer currentCustomer;
    public Division currentDivision;
    public Country currentCountry;
    public TextField postalTxt;
    public TextField nameTxt;
    public TextField phoneTxt;
    public Button commitButton;
    public Button startOverButton;
    public Button saveButton;
    public CheckBox addressPrompt;
    public CheckBox postalPrompt;
    public CheckBox phonePrompt;
    public CheckBox namePrompt;
    public int newCustomerID;
    public String newCustomerName;
    public String newAddress;
    public String newPostalCode;
    public String newPhone;
    public LocalDateTime newCreateDate;
    public String newCreatedBy;
    public Timestamp newLastUpdate;
    public String newLastUpdatedBy;
    public int newDivisionID;
    public User currentUser;
    public Label promptOverAddress;

    public void receiveCustomerToEdit(Customer customer) {
        if(customer == null){
            previewLabel.setText("Preview");
            customerNameLabel.setText("");
            streetAddressLabel.setText("");
            phoneLabel.setText("");
            stateProvinceZipLabel.setText("");
            countryLabel.setText("");
            newCustomerID = 90909;
            return;
        }
        currentCustomer = customer;
        newCustomerID = currentCustomer.getCustomerID();
        //Determine Country and Division
        for(Division d: AllDivisions){
            if(currentCustomer.getDivisionID() == d.getDivisionID()){
                currentDivision = d;
            }
        }
        for(Country c: AllCountries){
            if(currentDivision.getCountryID() == c.getCountryID()){
                currentCountry = c;
            }
        }
        //set labels
        customerNameLabel.setText(currentCustomer.getCustomerName());
        streetAddressLabel.setText(currentCustomer.getAddress());
        phoneLabel.setText(currentCustomer.getPhone());
        stateProvinceZipLabel.setText(currentDivision.getDivision() + ", " + currentCustomer.getPostalCode());
        countryLabel.setText(currentCountry.getCountry());
    }

    public void getDivision() {
        currentCountry = countryBox.getSelectionModel().getSelectedItem();
        currentCountry.getFirstLevelDivisions().clear();
        for (Division d : AllDivisions){
            if(currentCountry.getCountryID() == d.getCountryID()){
                currentCountry.getFirstLevelDivisions().add(d);
            }
        }
        divisionBox.setItems(currentCountry.getFirstLevelDivisions());
        countryBox.setDisable(true);
        startPrompt.setVisible(false);
        divPrompt.setVisible(true);
        divisionBox.setVisible(true);
        countryLabel.setText(currentCountry.getCountry());
    }

    public void getAddress() {
        currentDivision = divisionBox.getSelectionModel().getSelectedItem();
        newDivisionID = currentDivision.getDivisionID();
        divPrompt.setVisible(false);
        divisionBox.setDisable(true);
        addressPrompt.setVisible(true);
        promptOverAddress.setVisible(true);
        addressTxt.setVisible(true);
        next1.setVisible(true);
        if(currentCustomer == null){
            stateProvinceZipLabel.setText(currentDivision.getDivision() + ", ");
            System.out.println(currentDivision.getDivision());
            return;
        }
        stateProvinceZipLabel.setText(currentDivision.getDivision() + ", " + currentCustomer.getPostalCode());
        System.out.println(currentDivision.getDivision());
    }

    public void getPostalCode() {
        //check for null values.
        if(addressTxt.getText().isEmpty()){
            Alerts("fill in the blank");
            return;
        }
        addressPrompt.setVisible(false);
        promptOverAddress.setVisible(false);
        addressTxt.setVisible(false);
        next1.setVisible(false);
        postalPrompt.setVisible(true);
        postalTxt.setVisible(true);
        next2.setVisible(true);
        streetAddressLabel.setText(addressTxt.getText());
        newAddress = addressTxt.getText();
        System.out.println(newAddress);

    }

    public void getPhone() {
        if(postalTxt.getText().isEmpty()){
            Alerts("fill in the blank");
            return;
        }
        postalPrompt.setVisible(false);
        postalTxt.setVisible(false);
        next2.setVisible(false);
        phonePrompt.setVisible(true);
        phoneTxt.setVisible(true);
        next3.setVisible(true);
        if(currentCustomer == null) {
            stateProvinceZipLabel.setText(stateProvinceZipLabel.getText() + postalTxt.getText());
            newPostalCode = postalTxt.getText();
            System.out.println(newPostalCode);
            return;
        }
        stateProvinceZipLabel.setText(currentDivision.getDivision() +", " + postalTxt.getText());
        newPostalCode = postalTxt.getText();
        System.out.println(newPostalCode);
    }

    public void getName() {
        if(phoneTxt.getText().isEmpty()){
            Alerts("fill in the blank");
            return;
        }
        phoneTxt.setVisible(false);
        phonePrompt.setVisible(false);
        next3.setVisible(false);
        namePrompt.setVisible(true);
        nameTxt.setVisible(true);
        commitButton.setVisible(true);
        phoneLabel.setText(phoneTxt.getText());
        newPhone = phoneTxt.getText();
        System.out.println(newPhone);
    }

    public void commit() {
        if(nameTxt.getText().isEmpty()){
            Alerts("fill in the blank");
            return;
        }
        nameTxt.setVisible(false);
        namePrompt.setVisible(false);
        commitButton.setVisible(false);
        saveButton.setDisable(false);
        startOverButton.setDisable(false);
        customerNameLabel.setText(nameTxt.getText());
        newCustomerName = nameTxt.getText();
        System.out.println(newCustomerName + "1");
    }

    public void leaveAddress() {
        if(currentCustomer == null){
            Alerts("fill in the blank");
            addressPrompt.setSelected(false);
            return;
        }
        addressTxt.setText(currentCustomer.getAddress());
        getPostalCode();
    } //FIXME not tested

    public void leavePostalCode() {
        if(currentCustomer == null){
            Alerts("fill in the blank");
            postalPrompt.setSelected(false);
            return;
        }
        postalTxt.setText(currentCustomer.getPostalCode());
        getPhone();
    }//FIXME not Tested

    public void leavePhone() {
        if (currentCustomer == null){
            Alerts("fill in the blank");
            phonePrompt.setSelected(false);
            return;
        }
        phoneTxt.setText(currentCustomer.getPhone());
        phonePrompt.setSelected(false);
        getName();
    }//FIXME

    public void leaveName() {
        if (currentCustomer == null){
            Alerts("fill in the blank");
            namePrompt.setSelected(false);
            return;
        }
        nameTxt.setText(currentCustomer.getCustomerName());
        commit();
    }

    public void saveCustomer() throws SQLException, IOException {
        if ((currentCustomer != null) && (newCustomerName.equals(currentCustomer.getCustomerName())) &&
                (newDivisionID == currentCustomer.getDivisionID()) &&(newAddress.equals(currentCustomer.getAddress())) &&
                (newPhone.equals(currentCustomer.getPhone())) && (newPostalCode.equals(currentCustomer.getPostalCode()))){
            Alerts("no changes were made");
            Stage stage =(Stage) previewLabel.getScene().getWindow();
            sendCustomerToEdit(currentCustomer, stage);
            return;
        }
        //Make sure length of string is acceptable for the database.
        if(stringTooLong(newCustomerName, newAddress, newPhone, newPostalCode)){
            return;
        }
        if(currentCustomer == null){
            newCreateDate = LocalDateTime.now();
            newCreatedBy = currentUser.getUserName();
        }
        else{
            newCreatedBy = currentCustomer.getCreatedBy();
            newCreateDate = currentCustomer.getCreateDate();
        }
        newLastUpdate = Timestamp.from(Instant.now());
        newLastUpdatedBy = currentUser.getUserName();
        System.out.println(newCustomerName + "2");
        Customer c = new Customer(newCustomerID,newCustomerName,newAddress,newPostalCode,newPhone,newCreateDate,newCreatedBy,
                newLastUpdate,newLastUpdatedBy,newDivisionID);
        Stage stage = (Stage) previewLabel.getScene().getWindow();
        if(c.getCustomerID() < 90909){
            System.out.println(c.getCustomerName());
            DBCustomer.updateCustomer(c);
            AllCustomers.clear();
            DBCustomer.selectAllCustomers();
            Alerts("updated a customer");
            passTheCustomer(c,stage);
            return;
        }
        DBCustomer.insertCustomer(c);
        AllCustomers.clear();
        DBCustomer.selectAllCustomers();
        Alerts("created a customer");
        passTheCustomer(c,stage);
    }

    /**
     * Navigate to the homepage.
     */
    public void toHomePage() throws IOException {
        Stage stage = (Stage) previewLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    }

    /**
     * Returns the user to a blank form.
     */
    public void clearForm() throws IOException {
        Stage stage = (Stage) previewLabel.getScene().getWindow();
        sendCustomerToEdit(currentCustomer, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllCountries.clear();
        try {
            DBCountry.selectAllCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        countryBox.setItems(AllCountries);

        for (User u : AllUsers){
            if(getActiveUser(null).getUserID() == u.getUserID()){
                currentUser = u;
                return;
            }
        }
    }




}
