package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.util.Objects;

import static model.Contact.deleteContactAppointment;
import static model.Country.deleteCountryCustomer;
import static model.Customer.customerAppointmentList;
import static model.Customer.deleteCustomerAppointment;
import static model.FirstLevelDivision.deleteDivisionCustomer;
import static model.User.deleteUserAppointment;

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
            case "Deleted appointment" -> alert.setContentText("You have successfully deleted the selected appointment");
            case "Deleted associated appointments" -> alert.setContentText("You have successfully deleted all of this customer's appointments.");
            case "Deleted customer" -> alert.setContentText("You have successfully deleted this customer and all associated appointments");
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
                JDBC.closeConnection();
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
                JDBC.closeConnection();
                System.exit(0);
            }
        });
    }

    /**
     * Method to delete appointments part 1.
     */
    public static void deleteAppointmentFromAll(Appointment appointment){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete this appointment?");
        alert.setContentText("Are you sure you want to delete this appointment? \n" +
                "Deleted appointments can not be recovered. \n" +
                "Press OK to continue or Cancel to go back.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                deleteAppointmentFromAllPart2(appointment);
            }
        });
    }

    /**
     * Method allows for a loop to iterate through and delete associated appointments from a customer abject.
     */
    public static void deleteAppointmentFromAllPart2(Appointment appointment){
        deleteUserAppointment(appointment);
        deleteCustomerAppointment(appointment);
        deleteContactAppointment(appointment);
        Alerts("Deleted appointment");
    }

    /**
     * Method to delete a customer.
     */
    public static void deleteCustomerFromAll(Customer customer){
        Alert alert1 =new Alert(Alert.AlertType.CONFIRMATION);                          //Confirm with user the deletion of all associated appointments.
        alert1.setTitle("You can't do that yet.");
        alert1.setContentText("In order to delete a customer from this system\n" +
                "you first need to delete all of their associated appointments. \n" +
                "Do you wish to do that now?");
        alert1.showAndWait().ifPresent(response1 -> {
            if (response1 == ButtonType.OK) {
                int i;
                for (i = 0; i < Customer.customerAppointmentList.size(); i++){          //Iterate through all associated appointments and delete them.
                   Appointment choppedAppointment = customerAppointmentList.get(i);
                   deleteAppointmentFromAllPart2(choppedAppointment);
                }
            }
        });
        Alerts("Deleted associated appointments");                              //Give the user feedback about deleting the customer's appointments.

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);                         //Confirm the deletion of a Customer object.
        alert2.setTitle("Delete this customer?");
        alert2.setContentText("Are you sure you want to delete this customer? \n" +
                "Deleted customers can not be recovered. \n" +
                "Press OK to continue or Cancel to go back.");
        alert2.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                deleteCountryCustomer(customer);
                deleteDivisionCustomer(customer);
                Alerts("Deleted Customer");
            }
        });
    }


}
