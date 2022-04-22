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
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        Email = email;
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
        Contact.contactAppointments = contactAppointments;
    }

    /**
     * Create a list for associated appointments
     */
    static ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

    /**
     * Method to add an appointment to the appointments list.
     */
    public void addContactAppointment(Appointment appointment){
        contactAppointments.add(appointment);
    }

    /**
     * Method to delete an appointment from appointments list.
     */
    public static void deleteContactAppointment(Appointment appointment){
        contactAppointments.remove(appointment);
    }

    /**
     * Method to show contact appointments.
     */
    public ObservableList<Appointment> getContactAppointments(){
        return contactAppointments;
    }

    /**
     * Override to allow for readability in the combo boxes
     */
    @Override
    public String toString(){
        return (contactName + "   " + "ID: " + contactID);
    }
}
