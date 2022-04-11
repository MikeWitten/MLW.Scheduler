package controller;

import DAO.DBAppointment;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.User.deleteUserAppointment;
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

    //Methods
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
        //Alert the user that deleted items cannot be recovered.
        Appointment appointment = AppointmentTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete " + appointment.getTitle() + " from the appointment list.");
        alert.setTitle("Delete this Item? ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                //If the user agrees to delete the appointment, delete the appointment from associated user list as well as
                //from the AllAppointments list and the database.
                deleteUserAppointment(appointment);
                AllAppointments.remove(appointment);
                AppointmentTable.getSelectionModel().clearSelection();
                try {
                    DBAppointment.deleteApt(appointment);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    } //FIXME can this be sent to methods?

    /**
     * Set up the table for associated appointments.
     */
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> appointmentID;
    public TableColumn<Object, Object> customerID;
    public TableColumn<Object, Object> title;
    public TableColumn<Object, Object>  startTime;
    public TableColumn<Object, Object> endTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get active user data and populate text fields
        userIDTxt.setText(String.valueOf(getActiveUser(null).getUserID()));
        passwordTxt.setText(getActiveUser(null).getPassword());
        userNameTxt.setText(getActiveUser(null).getUserName());
        createDateTxt.setText(String.valueOf(getActiveUser(null).getCreateDate()));
        createdByTxt.setText(getActiveUser(null).getCreatedBy());
        lastUpdatedTxt.setText(String.valueOf(getActiveUser(null).getLastUpdate()));
        lastUpdatedByTxt.setText(getActiveUser(null).getLastUpdatedBy());

        //Determine the user.
        for (User allUser : AllUsers) {
            if (allUser.getUserID() == getActiveUser(null).getUserID()) {
                currentUser = allUser;
            }
        }

        //Populate the table.
        assert currentUser != null;
        AppointmentTable.setItems(currentUser.getUserAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("end"));

    }
}
