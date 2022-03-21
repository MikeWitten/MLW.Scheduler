package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Methods {
    /**
     * Create a universal method for notification alerts throughout the program.
     */
    public static void Alerts(String alertType){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Something went wrong");
        alert.setHeight(550);
        alert.setWidth(550);
        switch (alertType) {
            case "no item selected" -> alert.setContentText("You haven't selected anything.");
            case "no upcoming appointments" -> alert.setContentText("There are no upcoming appointments.");
        }
        alert.show();
    }

    /**
     * Method to navigate between main pages.
     */
    public static void navigation(Stage stage, String string) throws IOException {
        System.out.println(string);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Methods.class.getResource(string)));
        Scene scene = new Scene(root, 1000, 550);
        stage.setScene(scene);
        stage.setTitle("Desktop Scheduling Application");
        stage.show();
    }

    /**
     * Method to log out from each screen.
     */
    public static void logOutHere(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Desktop Scheduling Application");
        alert.setContentText("To log out press OK.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                Parent root = stage.getScene().getRoot();
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(Methods.class.getResource("/view/Log In.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 1000, 550);
                stage.setTitle("Log In");
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    /**
     * Method to exit the program.
     */
    public static void exitHere(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Desktop Scheduling Application");
        alert.setContentText("To Exit press OK");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                System.exit(0);
            }
        });
    }

}
