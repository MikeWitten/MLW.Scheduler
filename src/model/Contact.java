package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {
    //Create Contact object arguments.
    int contactID;          //FIXME unique value.
    String contactName;     //FIXME 50 char limit. (Alert already exists)
    String Email;           //FIXME check valid email format.

    /**
     * Create a constructor for the Contact objects.
     */
    public Contact(int contactID, String contactName, String email, ObservableList<Appointment> contactAppointments) {
        this.contactID = contactID;
        this.contactName = contactName;
        Email = email;
        this.contactAppointments = contactAppointments;
    }

    /**
     * Create getters and setters for Contact objects.
     */
    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setContactAppointments(ObservableList<Appointment> contactAppointments) {
        this.contactAppointments = contactAppointments;
    }

    /**
     * Create a list for associated appointments
     */
    ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

    /**
     * Method to add an appointment to the appointments list.
     */
    public void addContactAppointment(Appointment appointment){
        contactAppointments.add(appointment);
    }

    /**
     * Method to delete an appointment from appointments list.
     */
    public void deleteContactAppointment(Appointment appointment){
        contactAppointments.remove(appointment);
    }

    /**
     * Method to show contact appointments.
     */
    public ObservableList<Appointment> getContactAppointments(){
        return contactAppointments;
    }
}
