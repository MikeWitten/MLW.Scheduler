package model;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author
 * Micheal Lee Witten
 */
public class Main extends Application {

    /**
     * Start the application by loading the "Log In" screen.
     */
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load((getClass().getResource("/view/LogIn.fxml")));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 400, 600));
        stage.show();
    }

    public static void main(String[] args) {
        //Load test information for tables.
        //loadDefaultSet();

        launch(args);
    }
}
