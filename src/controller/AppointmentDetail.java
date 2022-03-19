package controller;

import javafx.event.ActionEvent;

public class AppointmentDetail {
    /**
     * Identify Appointment object
     */
    Appointment thisAppointment;

    /**
     * Receive the appointment object.
     */
    public void sendAppointment(Appointment appointment){
        thisAppointment= appointment;
    }


    public void cancel(ActionEvent actionEvent) {
    }

    public void saveChanges(ActionEvent actionEvent) {
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void editAppointment(ActionEvent actionEvent) {
    }


}
