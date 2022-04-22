package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Division;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static utilities.Methods.*;

public class CountryDetails{


    public Label stageLabel;
    public Label countryIDLabel;
    public Label countryLabel;
    public Label createdByLabel;
    public Label createDateLabel;
    public Label updatedByLabel;
    public Label lastUpdateLabel;
    public TableView<Division> DivisionsTable;
    public TableColumn<Object, Object> divisionID;
    public TableColumn<Object, Object> division;
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
    public void toFirstLevelDivision() throws IOException {
        if(DivisionsTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Division div = DivisionsTable.getSelectionModel().getSelectedItem();
        passTheDivision(div, stage);
    }

    /**
     * Formatter to help usability.
     */
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd 'of' yyyy 'at' HH:mm a"); //FIXME not tested.

    /**
     * Receive the country object information from the previous screen.
     */
    public void receiveObject(Country country) {
        currentCountry = country;
        System.out.println(currentCountry.getCountry()); //FIXME Checked!
        //Ensure division data is up to date.
        currentCountry.getFirstLevelDivisions().clear();
        for(Division d: AllDivisions){
            if(currentCountry.getCountryID() == d.getCountryID()){
                currentCountry.getFirstLevelDivisions().add(d);
            }
        }
        //Populate the table.
        DivisionsTable.setItems(currentCountry.getFirstLevelDivisions());
        divisionID.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        division.setCellValueFactory(new PropertyValueFactory<>("division"));
        //Set the labels.
        countryIDLabel.setText(String.valueOf(currentCountry.getCountryID()));
        countryLabel.setText(currentCountry.getCountry());
        createdByLabel.setText(currentCountry.getCreatedBy());
        createDateLabel.setText(formatter.format(currentCountry.getCreateDate()));
        updatedByLabel.setText(currentCountry.getLastUpdatedBy());
        lastUpdateLabel.setText(formatter.format(currentCountry.getLastUpdate().toLocalDateTime()));
    }
}
