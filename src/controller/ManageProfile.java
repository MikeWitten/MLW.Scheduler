package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class ManageProfile implements Initializable {
    public Label stageLabel;
    public TextField userIDTxt;
    public TextField userNameTxt;
    public TextField passwordTxt;
    public TextField createDateTxt;
    public TextField createdByTxt;
    public TextField lastUpdatedTxt;
    public TextField lastUpdatedByTxt;

    User currentUser;
    Appointment appointment;

    //Navigation
    public void toExit() {
        //Method found in utilities.methods.
        exitHere();
    }

    public void toLogOut() {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        logOutHere(stage);
    }

    public void toYourProfile() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Profile.fxml");
    }

    public void toAppointmentManager() throws IOException{
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Appointments.fxml");
    }

    public void toCustomerManager() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Manage Customers.fxml");
    }

    public void toHome() throws IOException {
        //Method found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        navigation(stage, "/view/Home Page.fxml");
    }

    /**
     * Navigate to the appointment details page.  Pass all appointment information.
     */
    public void toAppointmentDetails() throws IOException {
        //Ensure an appointment is selected.
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null){
            Alerts("no item selected");
            return;
        }
        //Pass the information. Found in utilities.methods.
        Stage stage1 = (Stage) (stageLabel).getScene().getWindow();
        Appointment currentAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        passTheAppointment(currentAppointment, stage1);
    }

    /**
     *Navigate to the appointment details screen.  Pass the user information
     */
    public void addAppointment() throws IOException {
        //Passes user information to create a new appointment object. Found in utilities.methods.
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        passTheUser(currentUser, stage);
    }

    /**
     * Navigate to the appointment details page.  Pass all appointment information.
     */
    public void editAppointment() throws IOException {
        //uses same method as the view appointment detail button.
        toAppointmentDetails();
    }

    /**
     * Method to delete an appointment from the user's schedule.
     */
    public void deleteAppointment() {
        //Ensure that an appointment is selected.
        if(AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alerts("no item selected");
            return;
        }
        else
            appointment = AppointmentTable.getSelectionModel().getSelectedItem();
        //Method found in utilities.methods.
            deleteAppointmentFromAll(appointment, currentUser, null, null);
        Alerts("Deleted appointment");
        AppointmentTable.refresh();
    }

    /**
     * Set up the table for associated appointments.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> title;
    public TableColumn<Object, Object> dateColumn;
    public TableColumn<Object, Object>  startTime;
    public TableColumn<Object, Object> endTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get active user data and populate text fields
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd 'of' yyyy 'at' hh:mm a" );
        userIDTxt.setText(String.valueOf(getActiveUser(null).getUserID()));
        passwordTxt.setText(getActiveUser(null).getPassword());
        userNameTxt.setText(getActiveUser(null).getUserName());
        createDateTxt.setText(formatter.format(getActiveUser(null).getCreateDate()));
        createdByTxt.setText(getActiveUser(null).getCreatedBy());
        lastUpdatedTxt.setText(formatter.format(getActiveUser(null).getLastUpdate().toLocalDateTime()));
        lastUpdatedByTxt.setText(getActiveUser(null).getLastUpdatedBy());

        //Determine the user.
        for (User allUser : AllUsers) {
            if (allUser.getUserID() == getActiveUser(null).getUserID()) {
                currentUser = allUser;
            }
        }
        //If another user was logged on previously, clear their associated appointments.
        assert currentUser != null;
        if (currentUser.getUserAppointments().size() > 0 ){
            currentUser.getUserAppointments().clear();
        }

        //Populate the current user's associated appointments.
        if(currentUser.getUserAppointments().isEmpty()) {
            for (Appointment allAppointment : AllAppointments) {
                if (allAppointment.getUserID() == currentUser.getUserID()) {
                    currentUser.addUserAppointment(allAppointment);
                }
            }
        }

        //Populate the table.
        assert currentUser != null;
        AppointmentTable.setItems(currentUser.getUserAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("parsedStartDate"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("parsedStartTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("parsedEndTime"));

    }
}
