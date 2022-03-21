package utilities;

/**
 * Create an Active User class that will allow me to navigate and populate fields.  This is called the Singleton function.
 * https://docs.oracle.com/cd/E93962_01/bigData.Doc/eql_onPrem/src/reql_sets_singleton.html
 */
public final class ActiveUser {

    /**
     * Create arguments for the Active User object.
     */
    private static ActiveUser activeUser;
    private String userName;


    /**
     * Create a constructor for the Active User object.
     */
    private ActiveUser(String userName){
        this.userName = userName;
    }

    /**
     * Create getter and setter for Active User object.
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method to create one instance of Active User upon log in.
     */
    public static ActiveUser getActiveUser(String userName){
        if (activeUser == null){
            activeUser = new ActiveUser(userName);
        }
        return activeUser;
    }

    /**
     * Method to delete activeUser upon log out.
     */
    public void voidActiveUser(){
        activeUser.setUserName("");
    }
}
