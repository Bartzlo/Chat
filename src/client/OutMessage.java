package client;

import server.Message;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Created by Bart on 25.08.2014.
 */
public class OutMessage extends Thread {

    Scanner UserMesSc = new Scanner(System.in);
    Message message;
    ObjectOutputStream outStream;

    public OutMessage(ObjectOutputStream out){
        outStream = out;
    }

    public void run(){
        try{
            while (true) {
                message = new Message(null, UserMesSc.nextLine(), null);
                outStream.writeObject(message);
                outStream.flush();
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }
}
