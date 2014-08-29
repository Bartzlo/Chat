package common;

import java.util.Date;

/**
 * Created by Bartzlo on 29.08.2014.
 */
public class PrintOut {

    public static void printMessasge(Message mes){
        String name = mes.getUserName();
        String mesStr = mes.getMessage();
        String date = mes.getDate().toString();

        System.out.println(date + " | " + name + ": " + mesStr);
    }
}
