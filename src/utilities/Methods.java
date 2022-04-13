package utilities;

import DAO.DBAppointment;
import controller.AppointmentDetails;
import controller.ContactDetails;
import controller.CustomerDetails;
import controller.UserDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static model.Contact.deleteContactAppointment;
import static model.Customer.customerAppointmentList;
import static model.Customer.deleteCustomerAppointment;
import static model.User.deleteUserAppointment;
import static utilities.ActiveUser.voidActiveUser;

public class Methods {
    //Access the localization bundles to translate messages.
    static Locale currentLocale = Locale.getDefault();
    static ResourceBundle bundle = ResourceBundle.getBundle("resources.myBundle", currentLocale);

    /**
     * Method for notification alerts throughout the program.
     */
    public static void Alerts(String alertType){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("errorMessage"));
        alert.setHeight(550);
        alert.setWidth(550);
        switch (alertType) {
            case "no item selected" -> alert.setContentText("You haven't selected anything.");
            case "no upcoming appointments" -> alert.setContentText("There are no upcoming appointments.");
            case "Deleted appointment" -> alert.setContentText("You have successfully deleted the selected appointment");
            case "Deleted associated appointments" -> alert.setContentText("You have successfully deleted all of this customer's appointments.");
            case "Deleted customer" -> alert.setContentText("You have successfully deleted this customer and all associated appointments");
            case "User Name and Password required" -> alert.setContentText(bundle.getString("credentialMessage"));
            case "You have an upcoming appointment" -> alert.setContentText("You have an appointment starting in less than 15 minutes!");
            case "make editable" -> alert.setContentText("Please select the 'Add/Edit' button to make changes to the selected Item");
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
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("resources.myBundle", currentLocale);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Desktop Scheduling Application");
        alert.setContentText("To log out press OK.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                //Close the connection to the database.
                JDBC.closeConnection();
                //delete the active user.
                voidActiveUser();
                Parent root = stage.getScene().getRoot();
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(Methods.class.getResource("/view/Log In.fxml")), bundle);
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
        alert.setTitle(bundle.getString("exitTitle"));
        alert.setContentText(bundle.getString("exitMessage"));
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                //Close the connection to the database.
                JDBC.closeConnection();
                System.exit(0);
            }
        });
    }

    /**
     * Method to 'pass the appointment football' to appointment details page.
     */
    public static void passTheAppointment(Appointment appointment, Stage stage1) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/Appointment Details.fxml"));
        loader.load();
        AppointmentDetails controller = loader.getController();
        controller.receiveAppointment(appointment);
        Parent scene = loader.getRoot();
        stage1.setTitle("Desktop Scheduling Application");
        stage1.setScene(new Scene(scene));
        stage1.show();
    }

    /**
     * Method to 'pass the User football' to appointment details page.
     */
    public static void passTheUser(User user, Stage stage1) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/Appointment Details.fxml"));
        loader.load();
        AppointmentDetails controller = loader.getController();
        controller.receiveUser(user);
        Parent scene = loader.getRoot();
        stage1.setTitle("Desktop Scheduling Application");
        stage1.setScene(new Scene(scene));
        stage1.show();
    }

    /**
     * Pass the customer football to the customer details page.
     */
    public static void passTheCustomer(Customer customer, Stage stage1) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/Customer Details.fxml"));
        loader.load();
        CustomerDetails controller = loader.getController();
        controller.receiveCustomer(customer);
        Parent scene = loader.getRoot();
        stage1.setTitle("Desktop Scheduling Application");
        stage1.setScene(new Scene(scene));
        stage1.show();
    }

    /**
     * Pass the user football to the user details page.
     */
    public static void passTheUserToUser(User user, Stage stage1) throws IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Methods.class.getResource("/view/User Details.fxml"));
            loader.load();
            UserDetails controller = loader.getController();
            controller.receiveTheUser(user);
            Parent scene = loader.getRoot();
            stage1.setTitle("Desktop Scheduling Application");
            stage1.setScene(new Scene(scene));
            stage1.show();
        }

    public static void passTheContact(Contact contact, Stage stage1) throws IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Methods.class.getResource("/view/Contact Details.fxml"));
            loader.load();
            ContactDetails controller = loader.getController();
            controller.receiveTheContact(contact);
            Parent scene = loader.getRoot();
            stage1.setTitle("Desktop Scheduling Application");
            stage1.setScene(new Scene(scene));
            stage1.show();

    }

    /**
     * Method to delete appointments part 1.
     */
    public static void deleteAppointmentFromAll(Appointment appointment, User user, Customer customer, Contact contact){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete this appointment?");
        alert.setContentText("""
                Are you sure you want to delete this appointment?\s
                Deleted appointments can not be recovered.\s
                Press OK to continue or Cancel to go back.""");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                try {
                    deleteAppointmentFromAllPart2(appointment, user, customer, contact);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Method allows for a loop to iterate through and delete associated appointments from a customer abject.
     */
    public static void deleteAppointmentFromAllPart2(Appointment appointment, User user, Customer customer, Contact contact) throws SQLException {
        if(user != null){deleteUserAppointment(appointment);}
        if(customer !=null){ deleteCustomerAppointment(appointment);}
        if(contact !=null){ deleteContactAppointment(appointment);}
        AllAppointments.remove(appointment);
        DBAppointment.deleteApt(appointment);
    }

    /**
     * Method to delete a customer.
     */
    public static void deleteCustomerFromAll(Customer customer){
        //Check to see if the customer has any associated appointments and delete them.
        if(!customer.getAllCustomerAppointments().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);                          //Confirm with user the deletion of all associated appointments.
            alert1.setTitle("You can't do that yet.");
            alert1.setContentText("""
                    In order to delete a customer from this system
                    you first need to delete all of their associated appointments.\s
                    Do you wish to do that now?""");
            alert1.showAndWait().ifPresent(response1 -> {
                if (response1 == ButtonType.OK) {
                    int i;
                    for (i = 0; i < Customer.customerAppointmentList.size(); i++) {          //Iterate through all associated appointments and delete them.
                        Appointment choppedAppointment = customerAppointmentList.get(i);
                        try {
                            deleteAppointmentFromAllPart2(choppedAppointment, null, null, null);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            Alerts("Deleted associated appointments");                              //Give the user feedback about deleting the customer's appointments.
        }
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);                         //Confirm the deletion of a Customer object.
        alert2.setTitle("Delete this customer?");
        alert2.setContentText("""
                Are you sure you want to delete this customer?\s
                Deleted customers can not be recovered.\s
                Press OK to continue or Cancel to go back.""");
        alert2.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                Alerts("Deleted Customer");
            }
        });
    }

    /**
     * Methods to populate appointments list, to be used for table population
     */
    public static ObservableList<Appointment> AllAppointments= FXCollections.observableArrayList();

    public static void addApt(Appointment appointment){AllAppointments.add(appointment); }

    public static ObservableList<Appointment> getAllAppointments(){
        return AllAppointments;
    }

    /**
     * Methods to populate customers list, to be used for table population
     */
    public static ObservableList<Customer> AllCustomers= FXCollections.observableArrayList();

    public static void addCustomer(Customer customer){AllCustomers.add(customer); }

    public static void deleteCustomer(Customer customer){
        for (int j = 0; j <AllCustomers.size() ; j++) {
            if(AllCustomers.get(j).getCustomerID() == customer.getCustomerID()){
                AllCustomers.remove(j);
                deleteCustomer(customer);
            }
        }
    }

    public static ObservableList<Customer> getAllCustomers(){
        return AllCustomers;
    }

    /**
     * Methods to populate contacts list, to be used for reference.
     */
    public static ObservableList<Contact> AllContacts = FXCollections.observableArrayList();

    public static void addContact(Contact contact) {AllContacts.add(contact);}

    public static ObservableList<Contact> getAllContacts(){return AllContacts;}

    /**
     * Method to populate countries list, to be used for reference.
     */
    public static ObservableList<Country> AllCountries = FXCollections.observableArrayList();

    public static void addCountry(Country country) {AllCountries.add(country); }

    public static ObservableList<Country> getAllCountries(){ return AllCountries; }

    /**
     * Method to populate a divisions list, to be used for reference and table population.
     */
    public static ObservableList<Division> AllDivisions = FXCollections.observableArrayList();

    public static void addDivision(Division division){ AllDivisions.add(division); }

    public static ObservableList<Division> getAllDivisions() { return AllDivisions; }

    /**
     * Method to populate a users list, to be used for reference.
     */
    public static ObservableList<User> AllUsers = FXCollections.observableArrayList();

    public static void addUser(User user) { AllUsers.add(user); }

    public static ObservableList<User> getAllUsers () { return AllUsers; }

}
