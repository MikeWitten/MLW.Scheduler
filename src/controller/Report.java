package controller;


import DAO.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static utilities.Methods.AllAppointments;

public class Report implements Initializable {
    @FXML
    Label timeDateStamp;
    ObservableList<Breakdown> BreakdownList = FXCollections.observableArrayList();
    int p = 0;
    int u = 0;
    int e = 0;
    public static class Breakdown {
        String typeO;
        int count;

        public Breakdown(String aptType, int count) {
            typeO = aptType;
            this.count = count;
        }

        public String getAptType() {
            return typeO;
        }


        public void setAptType(String aptType) {
            typeO = aptType;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private void getTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM'/'dd'/'yyyy  hh:mm a");
        timeDateStamp.setText( formatter.format(LocalDateTime.now()));
    }

    private void setTable(ObservableList<Appointment> AllAppointments){
        //Create new list
        for(Appointment a: AllAppointments){
            if(a.getType().toString().equalsIgnoreCase("proposal")){
                p = p+1;
            }
            else if(a.getType().toString().equalsIgnoreCase("update")){
                u = u+1;
            }else if (a.getType().toString().equalsIgnoreCase("evaluation")){
                e = e+1;
            }
        }
        Breakdown propApt = new Breakdown("Proposal", p);
        BreakdownList.add(propApt);
        Breakdown upApt = new Breakdown("Update", u);
        BreakdownList.add(upApt);
        Breakdown evApt = new Breakdown("Evaluation", e);
        BreakdownList.add(evApt);

        popTable(BreakdownList);
    }

    private void popTable(ObservableList<Breakdown> breakdownList) {
        ReportTable.setItems(breakdownList);
        appointmentTypes.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    public TableView<Breakdown> ReportTable;
    public TableColumn<Object, Object> appointmentTypes;
    public TableColumn<Object, Object> count;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTimeStamp();
        //Ensure the table is up-to-date.
        AllAppointments.clear();
        try {
            DBAppointment.selectAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTable(AllAppointments);
    }




}
