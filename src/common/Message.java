package common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    public String printMessage(){
        String name = this.getUserName();
        String mesStr = this.getMessage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String date = dateFormat.format(this.getDate());
        if (name == null) name = "";
        if (mesStr == null) mesStr = "";
        String mes = "(" + date + ") " + name + ": " + mesStr;
        return mes;
    }

}
