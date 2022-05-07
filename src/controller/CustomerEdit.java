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
    public Label example;
    public Label example1;
    public Label example11;
    public Label example111;
    public TextField streetNameTxt;
    public TextField localityTxt;
    public TextField cityTxt;
    public TextField streetNumTxt;
    String stNum;
    String stName;
    String locality;
    String city;
    String address;

    //Navigation.
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

    /**
     * Receive the customer object to edit.
     */
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

    /**
     * Method to move to the next step after choosing a country.
     */
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

    /**
     * Method to move to the next step after choosing a division.
     */
    public void getAddress() {
        currentDivision = divisionBox.getSelectionModel().getSelectedItem();
        newDivisionID = currentDivision.getDivisionID();
        divPrompt.setVisible(false);
        divisionBox.setDisable(true);
        addressPrompt.setVisible(true);
        streetNumTxt.setVisible(true);
        streetNameTxt.setVisible(true);
        localityTxt.setVisible(true);
        cityTxt.setVisible(true);
        example.setVisible(true);
        example1.setVisible(true);
        example11.setVisible(true);
        example111.setVisible(true);
        next1.setVisible(true);
        if(currentCustomer == null){
            stateProvinceZipLabel.setText(currentDivision.getDivision() + ", ");
            return;
        }
        stateProvinceZipLabel.setText(currentDivision.getDivision() + ", " + currentCustomer.getPostalCode());
    }

    /**
     * Method to move to the next step after entering an address.
     */
    public void getPostalCode() {
        //Check for null fields.
        if (containsNullValues(streetNameTxt.getText(), streetNumTxt.getText(), cityTxt.getText())){
            Alerts("Address fields required");
            return;
        }
        //Check street number for being a number.
        try{
            Integer.parseInt(streetNumTxt.getText());
            stNum = streetNumTxt.getText();
        } catch (NumberFormatException nfe){
            Alerts("Street Number required");
            return;
        }
        addressPrompt.setVisible(false);
        streetNumTxt.setVisible(false);
        streetNameTxt.setVisible(false);
        localityTxt.setVisible(false);
        cityTxt.setVisible(false);
        example.setVisible(false);
        example1.setVisible(false);
        example11.setVisible(false);
        example111.setVisible(false);
        next1.setVisible(false);
        postalPrompt.setVisible(true);
        postalTxt.setVisible(true);
        next2.setVisible(true);
        stName = streetNameTxt.getText();
        city = cityTxt.getText();
        if(localityTxt.getText().isEmpty()) {
            address = stNum +" " + stName +", " + city;
        }else{
            locality = localityTxt.getText();
            address = stNum +" " + stName +", " + locality + ", " + city;
        }
        streetAddressLabel.setText(address);
        newAddress = address;
    }

    /**
     * Method to move to the next step after entering the Postal Code.
     */
    public void getPhone() {
        if(postalTxt.getText().isEmpty()){
            Alerts("fill in the blank");
            return;
        }
        if(currentCountry.getCountryID() == 1){
            if(!isUSPattern()){
                return;
            }
        }
        else if(currentCountry.getCountryID() == 2){
            if(!isUKPattern()){
                return;
            }
        }else if(currentCountry.getCountryID() == 3){
            if(!isCAPattern()){
                return;
            }
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
            return;
        }
        stateProvinceZipLabel.setText(currentDivision.getDivision() +", " + postalTxt.getText());
        newPostalCode = postalTxt.getText();
    }

    /**
     * Methods to confirm valid postal code patterns.
     */
    private Boolean isCAPattern() {
        String testCA = postalTxt.getText();
        if(testCA.length() != 7){
            Alerts("Does not meet CA standards");
            return false;
        }
        if ((!(Character.isAlphabetic(testCA.charAt(0)))) ||
                (!(Character.isAlphabetic(testCA.charAt(2)))) ||
                (!(Character.isAlphabetic(testCA.charAt(5))))
        ){
            Alerts("Does not meet CA standards");
            return false;
        }
        if (!(Character.isDigit(testCA.charAt(1))) ||
                !(Character.isDigit(testCA.charAt(4))) ||
                !(Character.isDigit(testCA.charAt(6)))
        ) {
            Alerts("Does not meet CA standards");
            return false;
        }
        if (testCA.charAt(3) != ' ') {
            Alerts("Does not meet CA standards");
            return false;
        }
        return true;
    }
    private Boolean isUKPattern() {
        String testUK = postalTxt.getText();
        if (testUK.length() != 8) {
            return false;
        }
        if ((!(Character.isAlphabetic(testUK.charAt(0)))) ||
                (!(Character.isAlphabetic(testUK.charAt(2)))) ||
                (!(Character.isAlphabetic(testUK.charAt(5))))
        ) {
            return false;
        }
        if (!(Character.isDigit(testUK.charAt(1))) ||
                !(Character.isDigit(testUK.charAt(4))) ||
                !(Character.isDigit(testUK.charAt(6)))
        ) {
            return false;
        }
        if (testUK.charAt(3) != ' ') {
            Alerts("Does not meet UK standards");
            return false;
        }
        return true;
    }
    private Boolean isUSPattern() {
        String us = postalTxt.getText();
        if(us.length() != 5){
            Alerts("Does not meet US Postal code standard");
            return false;
        }
        try {
            Integer.parseInt(us);
        }catch (NumberFormatException nfe){
            Alerts("Does not meet US Postal code standard");
            return false;
        }
        return true;
    }

    /**
     * Method to move to the next step after entering the Phone number.
     */
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
    }

    /**
     * Method to make the save and start over features available.  The user can review changes on the screen.
     */
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
    }

    /**
     * Series of methods that allow the user to keep any imported customer data, rather than changing it.
     */
    public void leaveAddress() {
        if(currentCustomer == null){
            Alerts("fill in the blank");
            addressPrompt.setSelected(false);
            return;
        }
        addressPrompt.setVisible(false);
        streetNumTxt.setVisible(false);
        streetNameTxt.setVisible(false);
        localityTxt.setVisible(false);
        cityTxt.setVisible(false);
        example.setVisible(false);
        example1.setVisible(false);
        example11.setVisible(false);
        example111.setVisible(false);
        next1.setVisible(false);
        postalPrompt.setVisible(true);
        postalTxt.setVisible(true);
        next2.setVisible(true);
        newAddress = currentCustomer.getAddress();
    }
    public void leavePostalCode() {
        if(currentCustomer == null){
            Alerts("fill in the blank");
            postalPrompt.setSelected(false);
            return;
        }
        newPostalCode = currentCustomer.getPostalCode();
        postalPrompt.setVisible(false);
        postalTxt.setVisible(false);
        next2.setVisible(false);
        phonePrompt.setVisible(true);
        phoneTxt.setVisible(true);
        next3.setVisible(true);
    }
    public void leavePhone() {
        if (currentCustomer == null){
            Alerts("fill in the blank");
            phonePrompt.setSelected(false);
            return;
        }
        phoneTxt.setText(currentCustomer.getPhone());
        phonePrompt.setSelected(false);
        phoneTxt.setVisible(false);
        phonePrompt.setVisible(false);
        next3.setVisible(false);
        namePrompt.setVisible(true);
        nameTxt.setVisible(true);
        commitButton.setVisible(true);
        phoneLabel.setText(phoneTxt.getText());
        newPhone = currentCustomer.getPhone();
    }
    public void leaveName() {
        if (currentCustomer == null){
            Alerts("fill in the blank");
            namePrompt.setSelected(false);
            return;
        }
        nameTxt.setVisible(false);
        namePrompt.setVisible(false);
        commitButton.setVisible(false);
        saveButton.setDisable(false);
        startOverButton.setDisable(false);
        customerNameLabel.setText(nameTxt.getText());
        newCustomerName = currentCustomer.getCustomerName();
    }

    /**
     * Method to create or update a customer object in the application and the database.
     */
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
        Customer c = new Customer(newCustomerID,newCustomerName,newAddress,newPostalCode,newPhone,newCreateDate,newCreatedBy,
                newLastUpdate,newLastUpdatedBy,newDivisionID);
        Stage stage = (Stage) previewLabel.getScene().getWindow();
        if(c.getCustomerID() < 90909){
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Ensure the countries list is up-to-date.
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
