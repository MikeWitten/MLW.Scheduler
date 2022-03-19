package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Country {

    //Create Country object arguments.
    int countryID;              //FIXME unique value 10 char max
    String country;             //FIXME 50 char max  Alert(already exists)
    LocalDateTime createDate;
    String createdBy;           //FIXME 50 char max
    Timestamp lastUpdate;
    String lastUpdatedBy;       //FIXME 50 char max
}
