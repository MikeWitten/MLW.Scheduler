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

    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentDetail.fxml"));
        stage.setTitle("Title");
        stage.setScene(new Scene(root, 400, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
