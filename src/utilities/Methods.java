package utilities;

import DAO.DBAppointment;
import DAO.DBCustomer;
import controller.*;
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

import static DAO.DBAppointment.*;
import static model.Contact.deleteContactAppointment;
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
    public static void Alerts(String alertType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("errorMessage"));
        alert.setHeight(550);
        alert.setWidth(550);
        switch (alertType) {
            case "Select participants" -> alert.setContentText("Please select a customer, user, and contact for this appointment.");
            case "no item selected" -> alert.setContentText("You haven't selected anything.");
            case "fill in the blank" -> alert.setContentText("Please fill in the blank.");
            case "no changes were made" -> alert.setContentText("You have not changed anything about this customer." +
                    "No changes will be saved.");
            case "choose a country" -> alert.setContentText("Please select a country to continue.");
            case "no upcoming appointments" -> alert.setContentText("There are no upcoming appointments.");
            case "Deleted appointment" -> alert.setContentText("You have successfully deleted the selected appointment");
            case "Deleted associated appointments" -> alert.setContentText("You have successfully deleted all of this customer's appointments.");
            case "Deleted customer" -> alert.setContentText("You have successfully deleted this customer and all associated appointments");
            case "User Name and Password required" -> alert.setContentText(bundle.getString("credentialMessage"));
            case "You have an upcoming appointment" -> alert.setContentText("You have an appointment starting in less than 15 minutes!");
            case "Null value" -> alert.setContentText("All editable fields are required.");
            case "The passage of time is important  lol" -> alert.setContentText("Your meeting start time is before (or at the same time as)  your meeting end time." +
                    "Please check to ensure that you have chosen the correct times.");
            case "updated a customer" -> alert.setContentText("You have successfully updated this customer." +
                    "You are being routed to the customer details page.");
            case "created a customer" -> alert.setContentText("You have successfully created a customer, you are being routed to the customer details page.");
            case "Customer overlapping appointment" -> alert.setContentText("Customers are not allowed to have overlapping appointments." +
                    "Please remove the conflicting appointment, or schedule this appointment for another time.");
            case "Please select a date." -> alert.setContentText("Please select a date.");
            case "The appointment was not deleted" -> alert.setContentText("You selected cancel." +
                    "The appointment was not deleted and can be found in the Appointment manager.");
            case "This information hasn't been saved yet." -> alert.setContentText("This appointment doesn't exist yet, to start over press the clear all button.");
            case "make editable" -> alert.setContentText("Please select the 'Add/Edit' button to make changes.");
            case "String too long" -> alert.setContentText("Please limit your input to 50 total characters(including spaces).");
            case "No available times." -> alert.setContentText("There are no times available for this date.");
            case "appointment date has passed." -> alert.setContentText("The date or time you have chosen has already passed, would you like to continue?");
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
    public static void logOutHere(Stage stage) {
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("resources.myBundle", currentLocale);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Desktop Scheduling Application");
        alert.setContentText("To log out press OK.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
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
    public static void exitHere() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("exitTitle"));
        alert.setContentText(bundle.getString("exitMessage"));
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                //Close the connection to the database.
                JDBC.closeConnection();
                System.exit(0);
            }
        });
    }

    /**
     * Method to check if input fields are null.  Credit to kamwo @ https://stackoverflow.com/users/1943228/kamwo.
     * Many thanks for your contribution.
     */
    public static boolean containsNullValues(String... strings) {
        for (String s : strings) {
            if (s.isEmpty()) {
                Alerts("Null value");
                return true;
            }
        }
        return false;
    }

    /**
     * Method to ensure that string data adheres to the database 50-character limit.
     */
    public static boolean stringTooLong(String... strings) {
        for (String s : strings) {
            if (s.length() > 50) {
                Alerts("String too long");
                return true;
            }
        }
        return false;
    }

    /**
     * Method to pass the 'appointment football' to appointment details page.
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
     * Method to pass the 'user football' to appointment details page.
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
     * Method to pass the 'user football' to appointment details page.
     */
    public static void passTheContactToAptDetails(Contact contact, Stage stage1) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/Appointment Details.fxml"));
        loader.load();
        AppointmentDetails controller = loader.getController();
        controller.receiveContact(contact);
        Parent scene = loader.getRoot();
        stage1.setTitle("Desktop Scheduling Application");
        stage1.setScene(new Scene(scene));
        stage1.show();
    }

    /**
     * Method to pass the 'customer football to the appointment details page.
     */
    public static void passTheCustomerToAptDetails(Customer customer, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/Appointment Details.fxml"));
        loader.load();
        AppointmentDetails controller = loader.getController();
        controller.receiveCustomer(customer);
        Parent scene = loader.getRoot();
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method to pass the 'country football' to the country details page.
     */
    public static void passTheCountryToCountryDetails(Country country, Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/Country Details.fxml"));
        loader.load();
        CountryDetails controller = loader.getController();
        controller.receiveObject(country);
        Parent scene = loader.getRoot();
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method to pass the 'division football' to the first level divisions page.
     */
    public static void passTheDivisionToDivision(Division division, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/First Level Division.fxml"));
        loader.load();
        FirstLevelDivision controller = loader.getController();
        controller.receiveDivision(division);
        Parent scene = loader.getRoot();
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Pass the 'customer football' to the customer details page.
     */
    public static void passTheCustomer(Customer customer, Stage stage1) throws IOException, SQLException {
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
     * Pass the 'Division' to the division details page
     */
    public static void passTheDivision(Division division, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/First Level Division.fxml"));
        loader.load();
        FirstLevelDivision controller = loader.getController();
        controller.receiveDivision(division);
        Parent scene = loader.getRoot();
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Pass the 'user football' to the user details page.
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

    /**
     * Method to pass the 'contact football' to the contact details page.
     */
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
     * Method to pass the 'customer football' to the edit / add customer page.
     */
    public static void sendCustomerToEdit(Customer customer, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Methods.class.getResource("/view/Customer Edit.fxml"));
        loader.load();
        CustomerEdit controller = loader.getController();
        controller.receiveCustomerToEdit(customer);
        Parent scene = loader.getRoot();
        stage.setTitle("Desktop Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
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
                Alerts("Deleted appointment");
            } else Alerts("The appointment was not deleted");
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
                    for(Appointment a: customer.getAllCustomerAppointments()){
                        try {
                            deleteAppointmentFromAllPart2(a, null, null, null);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("we fu**D up");
                            return;
                        }
                    }
                }
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);                       //Give the user feedback about deleting the customer's appointments.
                alert3.setContentText("All of this user's associated appointments have been deleted.");
                alert3.showAndWait();
            });

        }
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);                         //Confirm the deletion of a Customer object.
        alert2.setTitle("Delete this customer?");
        alert2.setContentText("""
                Are you sure you want to delete this customer?\s
                Deleted customers can not be recovered.\s
                Press OK to continue or Cancel to go back.""");
        alert2.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                try {
                    DBCustomer.deleteCustomer(customer);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                AllCustomers.remove(customer);
                Alerts("Deleted customer");
            }
        });
    } //FIXME not tested

    /**
     * Method to add an appointment to the database and update the current AllAppointmentsList.
     */
    public static void addAppointmentToDB(Appointment appointment) throws SQLException {
        if(appointment.getAppointmentID() < 90909){
            //Methods found in DAO.DBAppointment.
            updateApt(appointment);
        }else{
            insertApt(appointment);
        }
        AllAppointments.clear();
        selectAllAppointments();
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

    /**
     * Method to populate appointments for users, contacts, and customers.
     */
    public static void populateAssociatedLists(Customer customer, User user, Contact contact){
        int customerID;
        int userID;
        int contactID;
        if(customer != null){
            customerID = customer.getCustomerID();
            for (Appointment apt: AllAppointments) {
                if(apt.getCustomerID() == customerID){
                    customer.getAllCustomerAppointments().add(apt);
                }
            }
        }
        if(user != null){
            userID = user.getUserID();
            for (Appointment apt: AllAppointments) {
                if (userID == apt.getUserID()){
                    user.getUserAppointments().add(apt);
                }
            }
        }
        if(contact != null){
            contactID = contact.getContactID();
            for (Appointment apt: AllAppointments){
                if(contactID == apt.getContactID()){
                    contact.getContactAppointments().add(apt);
                }
            }
        }
    }


}
