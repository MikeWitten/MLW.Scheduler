package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginPage {

    public Label label1;
    public Button logIn;

    public void validateToMain() throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(Objects.requireNonNull(getClass()).getResource("/view/MainScreen.fxml"))));
        Stage stage = (Stage) (label1).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 550);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void exitProgram() {
        Methods.ExitHere();
    }
}
