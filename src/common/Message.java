package common;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bart on 29.08.2014.
 */
public class Message implements Serializable {
    private String userName;
    private String message;
    private Date date;

    public Message (String userName, String message, Date date){
        this.userName = userName;
        this.message = message;
        this.date = date;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName (){
        return userName;
    }

    public String getMessage (){
        return message;
    }

    public Date getDate() {
        return date;
    }

}
