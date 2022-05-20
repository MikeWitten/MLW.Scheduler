package controller;

import DAO.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ResourceBundle;

import static utilities.ActiveUser.getActiveUser;
import static utilities.Methods.*;

public class ManageAppointments implements Initializable {
    public Label stageLabel;
    public Label monthOfTheYear;
    public RadioButton monthViewButton;
    public RadioButton weekViewButton;
    public Button prevMonthButton;
    public Button nextMonthButton;
    public Button prevWeekButton;
    public Button nextWeekButton;
    public Button reportButton;
    public User currentUser;
    public Label dateLabel;
    public LocalDate currentDate;
    public Month currentMonth;
    public static ObservableList<Appointment> monthlyList = FXCollections.observableArrayList();
    ObservableList<Appointment> weeklyList = FXCollections.observableArrayList();

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
     * Format the dates for better readability.
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Week' W 'of' MMM, yyyy");

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
     * Method to change the table to filter appointments by month.
     */
    public void toMonthView() {
        monthlyList.clear();
        reportButton.setVisible(true);
        monthViewButton.setSelected(true);
        monthViewButton.setDisable(true);
        nextMonthButton.setVisible(true);
        prevMonthButton.setVisible(true);
        nextWeekButton.setVisible(false);
        prevWeekButton.setVisible(false);
        weekViewButton.setDisable(false);
        weekViewButton.setSelected(false);
        //Determine the current month and create a list to populate the table.
        currentDate = LocalDate.now();
        currentMonth = currentDate.getMonth();
        dateLabel.setText("Appointments for " + currentMonth + ", " + currentDate.getYear());
        for(Appointment a : AllAppointments){
            if(a.getRawStart().getMonth() == currentMonth){
                monthlyList.add(a);
            }
        }
        //Populate the monthly appointments table.
        populateTheTable(monthlyList);
    }

    /**
     * Method to change the table to filter by week.
     */
    public void toWeekView() {
        weeklyList.clear();
        reportButton.setVisible(false);
        monthViewButton.setSelected(false);
        monthViewButton.setDisable(false);
        nextMonthButton.setVisible(false);
        prevMonthButton.setVisible(false);
        nextWeekButton.setVisible(true);
        prevWeekButton.setVisible(true);
        weekViewButton.setDisable(true);
        weekViewButton.setSelected(true);
        //Set the date label with the current week.
        currentDate = LocalDate.now();
        String weekLabel = formatter.format(currentDate);
        dateLabel.setText(weekLabel);
        int weekNumber = currentDate.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear());
        for(Appointment a : AllAppointments){
            if(weekNumber == a.getRawStart().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear())){
                weeklyList.add(a);
            }
        }
        populateTheTable(weeklyList);
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
     * Method to filter results to the next month.
     */
    public void previousMonthFilter() {
        monthlyList.clear();
        currentDate = currentDate.minusMonths(1);
        currentMonth = currentDate.getMonth();
        dateLabel.setText("Appointments for " + currentMonth + ", " + currentDate.getYear());
        for(Appointment a : AllAppointments){
            if(a.getRawStart().getMonth() == currentMonth){
                monthlyList.add(a);
            }
        }
        //Populate the monthly appointments table.
        populateTheTable(monthlyList);
    }

    /**
     * Method to filter results to the next month.
     */
    public void nextMonthFilter() {
        monthlyList.clear();
        currentDate = currentDate.plusMonths(1);
        currentMonth = currentDate.getMonth();
        dateLabel.setText("Appointments for " + currentMonth + ", " + currentDate.getYear());
        for(Appointment a : AllAppointments){
            if(a.getRawStart().getMonth() == currentMonth){
                monthlyList.add(a);
            }
        }
        //Populate the monthly appointments table.
        populateTheTable(monthlyList);
    }

    /**
     * Method to filter to previous week.
     */
    public void previousWeekFilter() {
        weeklyList.clear();
        currentDate = currentDate.minusWeeks(1);
        String weekLabel = formatter.format(currentDate);
        dateLabel.setText(weekLabel);
        int weekNumber = currentDate.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear());
        for(Appointment a : AllAppointments){
            if(weekNumber == a.getRawStart().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear())){
                weeklyList.add(a);
            }
        }
        populateTheTable(weeklyList);
    }

    /**
     * Method to filter to next week.
     */
    public void nextWeekFilter() {
        weeklyList.clear();
        currentDate = currentDate.plusWeeks(1);
        String weekLabel = formatter.format(currentDate);
        dateLabel.setText(weekLabel);
        int weekNumber = currentDate.get(WeekFields.SUNDAY_START.weekOfWeekBasedYear());
        for(Appointment a : AllAppointments){
            if(weekNumber == a.getRawStart().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear())){
                weeklyList.add(a);
            }
        }
        populateTheTable(weeklyList);
    }

    /**
     * Generate a simple count of appointments for the month.
     */
    public void generateReport()throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        passTheList(currentDate, stage);
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
        toMonthView();
    }


}
