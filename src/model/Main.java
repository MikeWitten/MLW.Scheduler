package model;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

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
    public void start (Stage stage) throws Exception {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Log In.fxml")));
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


        //Load the user objects from the database so that username and password can be verified.
        selectAllUsers();

        //close the connection until the credentials are verified.
        JDBC.closeConnection();

        ResourceBundle rb = ResourceBundle.getBundle("src/utilities/Nat_fr.properties", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            System.out.println(rb.getString("Hello"));
        }
        launch(args);
    }
}
