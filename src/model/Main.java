package model;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author
 * Micheal Lee Witten
 */
public class Main extends Application {

    /**
     * Start the application by loading the "Log In" screen.
     */
    @Override
    public void start (Stage stage) throws Exception {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Log In.fxml")));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 1000, 550));
        stage.show();
    }

    public static void main(String[] args) {
        //Load test information for tables.
        //loadDefaultSet();

        launch(args);
    }
}
