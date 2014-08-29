package server;

import java.util.Date;

/**
 * Created by Bart on 29.08.2014.
 */
public class Message {
    private String userName;
    private String message;
    private Date date;

    public void Message (String userName, String message, Date date){
        this.userName = userName;
        this.message = message;
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
