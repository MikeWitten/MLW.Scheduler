package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class Country implements Initializable {
    public Label stageLabel;
    public TextField countryIDTxt;
    public TextField countryTxt;
    public TextField createdByTxt;
    public TextField createDateTxt;
    public TextField lastUpdatedByTxt;
    public TextField lastUpdatedTxt;
    public TextField divisionFilterTxt;

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
    }       //FIXME pass the User football.

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
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/First Level Division.fxml");
    } //FIXME pass the division football.

    /**
     * Create the first level divisions table.
     */
    public TableView<FirstLevelDivision> DivisionsTable;
    public TableColumn<Object, Object> divisionID;
    public TableColumn<Object, Object> division;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
