package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Methods.*;

public class LogIn implements Initializable {
    public TextField userNameTxt;
    public TextField passwordTxt;
    public Label stageLabel;    //Use to get stage for navigation.
    public TextField zoneIDTxt;
    public TextField dateTimeTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
//Navigation

    /**
     * Check credentials and navigate to home page.
     */
    public void credentialCheck()throws IOException {
        //FIXME Credential Check.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    }

    /**
     * Exit the program.
     */
    public void toExit() {
        exitHere();
    }
}
