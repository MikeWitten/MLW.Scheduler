package utilities;

import model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Create an Active User class that will allow me to navigate and populate fields.  This is called the Singleton function.
 * https://docs.oracle.com/cd/E93962_01/bigData.Doc/eql_onPrem/src/reql_sets_singleton.html
 */
public final class ActiveUser {

    /**
     * Create arguments for the Active User object.
     */
    private static ActiveUser activeUser;
    int userID;
    String userName;
    String password;
    LocalDateTime createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;


    /**
     * Create a constructor for the Active User object.
     */
    private ActiveUser(int userID, String userName, String password, LocalDateTime createDate, String createdBy,
                       Timestamp lastUpdate, String lastUpdatedBy){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Create getter and setter for Active User object.
     */
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Method to create one instance of Active User upon log in.
     */
   public static ActiveUser getActiveUser(User user){
       if (activeUser == null){
           activeUser = new ActiveUser( user.getUserID(), user.getUserName(), user.getPassword(),
                   user.getCreateDate(), user.getCreatedBy(), user.getLastUpdate(), user.getLastUpdatedBy() );
       }
        return activeUser;
    } 

    /**
     * Method to delete activeUser upon log out.
     */
    public static void voidActiveUser(){
        activeUser = null;
    }
}
