package common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bartzlo on 29.08.2014.
 */
public class PrintOut {

    public static void printMessage(Message mes){
        String name = mes.getUserName();
        String mesStr = mes.getMessage();
        String date = mes.getDate().toString();

        System.out.println(date + " | " + name + ": " + mesStr);
    }

    public static void printMessageClient(ArrayList<Message> array) throws IOException{

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n ============================\n ============================\n");
        for (Message mes : array){
        String name = mes.getUserName();
        String mesStr = mes.getMessage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        if (name == null) name = "";
        if (mesStr == null) mesStr = "";

        System.out.println("(" + dateFormat.format(mes.getDate()) + ") " + name + ": " + mesStr);}
        System.out.println("___________________________________");
    }
}
