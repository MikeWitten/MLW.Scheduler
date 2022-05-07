package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.TypeApt;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static controller.ManageAppointments.monthlyList;
import static utilities.Methods.navigation;

public class MonthlyAppointmentReport implements Initializable {
    public Label monthLabel;
    public Label stageLabel;
    public Label totalAppointmentsLabel;
    public TableView<TypeApt> typeTable;
    public TableColumn<Object, Object> typeColumn;
    public TableColumn<Object, Object> numberColumn;
    int number = 0;
    ObservableList<TypeApt> types = FXCollections.observableArrayList();
    ArrayList<String> typeStrings = new ArrayList<>();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM, yyyy");

    /**
     * Receive monthly list. Amended to receive the date.
     */
    public void receiveTheList(LocalDate date) {
        if (monthlyList.isEmpty()) {
            totalAppointmentsLabel.setText("There are no Appointments this month.");
            monthLabel.setText(formatter.format(date));
            return;
        }
        monthLabel.setText(formatter.format(date));
        totalAppointmentsLabel.setText("There are " + monthlyList.size() + " total appointments this month.");
        getTypesFromList();
    }

    /**
     * Method to create a new list of appointment types.
     */
    private void getTypesFromList() {
        for (Appointment a : monthlyList) {
            String temp = a.getType();
            if (typeStrings.isEmpty() || !typeStrings.contains(String.valueOf(temp))) {
                typeStrings.add(temp);
            }
        }
        for (String s : typeStrings) {
            for(Appointment a1: monthlyList){
                if(s.equals(a1.getType())){
                    number++;
                }
            }
            TypeApt typeApt = new TypeApt(s, number);
            types.add(typeApt);
            number = 0;
        }
        populateTable();
    }

    /**
     * Method to populate table.
     */
    private void populateTable() {
        typeTable.setItems(types);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
    }

    /**
     * Navigate back to Appointment Manager.
     */
    public void toAppointmentManager() throws IOException {
        Stage stage = (Stage) stageLabel.getScene().getWindow();
        String scene = "/view/Manage Appointments.fxml";
        navigation(stage, scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
