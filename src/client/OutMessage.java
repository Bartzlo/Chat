package client;

import java.io.DataOutputStream;
import java.util.Scanner;

/**
 * Created by Bart on 25.08.2014.
 */
public class OutMessage extends Thread {

    Scanner UserMesSc = new Scanner(System.in);

    String message;
    DataOutputStream outStream;

    OutMessage(DataOutputStream out){
        outStream = out;
    }

    public void run(){
        try{
            while (true) {
                message = UserMesSc.nextLine();
                outStream.writeUTF(message);
                outStream.flush();
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }
}
