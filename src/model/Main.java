package model;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static DAO.DBAppointment.selectAllAppointments;
import static DAO.DBUser.selectAllUsers;

/**
 * @author
 * Micheal Lee Witten
 * C195 -  30 March 2022
 */
public class Main extends Application {

    /**
     * Start the application by loading the "Log In" screen.
     */
    @Override
    public void start (Stage stage) throws IOException {
        //Get the Locale from the user's computer for localization.
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("resources.myBundle", currentLocale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Log In.fxml")),bundle);
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 1000, 550));
        stage.show();
    }

    /**
     * Launch the program.
     */
    public static void main(String[] args) throws SQLException {

        //Open the connection to the database to populate the user info.
        JDBC.openConnection();

        //Load the user objects from the database so that username and password can be verified
        // and appointments to create an alert for time sensitive appointments.
        selectAllUsers();
        selectAllAppointments();

        //close the connection until the credentials are verified.
        JDBC.closeConnection();

        launch(args);
    }
}
