package controller;

import DAO.DBAppointment;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class ManageAppointments implements Initializable {
    public Label stageLabel;
    public User currentUser;
    public TextField AppointmentSearch;
    public Button genReport;


    //Navigation
    public void toExit() {
        exitHere();
    }
    public void toLogOut() {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        logOutHere(stage);
    }
    public void toYourProfile() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Profile.fxml");
    }
    public void toAppointmentManager() throws IOException{
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Appointments.fxml");
    }
    public void toCustomerManager() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Customers.fxml");
    }
    public void toHome() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    }
    public void addAppointment() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        for(User u : AllUsers){
            if(getActiveUser(null).getUserID() == u.getUserID()){
                currentUser = u;

            }
        }
        navigation(stage, "/view/Appointment Details.fxml");
    }
    public void toAppointmentDetails() throws IOException {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        Appointment a = AppointmentTable.getSelectionModel().getSelectedItem();
        passTheAppointment(a, stage);
    }

    /**
     * Method to delete appointment.
     */
    public void deleteAppointment() throws IOException {
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        Appointment appointment = AppointmentTable.getSelectionModel().getSelectedItem();
        deleteAppointmentFromAll(appointment, null, null, null);
        AppointmentTable.refresh();
        toAppointmentManager();
    }

    /**
     * Method to populate the TableView.
     */
    public void populateTheTable(ObservableList<Appointment> observableList){
        AppointmentTable.setItems(observableList);
        aptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("parsedStartDate"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("parsedStartTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("parsedEndTime"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }




    /**
     * Set up Appointment Table.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> aptIDColumn;
    public TableColumn<Object, Object> titleColumn;
    public TableColumn<Object, Object> description;
    public TableColumn<Object, Object> locationColumn;
    public TableColumn<Object, Object> contactColumn;
    public TableColumn<Object, Object> type;
    public TableColumn<Object, Object> DateColumn;
    public TableColumn<Object, Object> startColumn;
    public TableColumn<Object, Object> endColumn;
    public TableColumn<Object, Object> customerColumn;
    public TableColumn<Object, Object> userID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Ensure the table is up-to-date.
        AllAppointments.clear();
        try {
            DBAppointment.selectAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Set initial table and buttons.
        populateTheTable(AllAppointments);

        //Creating an easy to use searchbar.
        //Wrap the Appointments Observable list in an appointments FilteredList initialized to display all data.
        FilteredList<Appointment> appointmentsFilteredList = new FilteredList<>(AllAppointments, a -> true);

        //Create a listener.
        AppointmentSearch.textProperty().addListener((observable, oldValue, newValue) -> appointmentsFilteredList.setPredicate(appointment -> {
            // Display all objects if text field is empty.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare appointment Title with filter text.
            String lowerCaseFilter = newValue.toLowerCase();
            if (appointment.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            //Compare appointment Type with filter text.
            else return String.valueOf(appointment.getType()).contains(lowerCaseFilter);
        }));

        //Wrap the filtered list in a sorted list, then bind the tableview.
        SortedList<Appointment> partSortedList = new SortedList<Appointment>(appointmentsFilteredList);
        partSortedList.comparatorProperty().bind(AppointmentTable.comparatorProperty());

        //Set the sorted and filtered objects into the TableView.
        AppointmentTable.setItems(partSortedList);

    }


    public void generateReport() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/Report.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
    }
}
