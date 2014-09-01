package client;

import common.Message;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Bart on 25.08.2014.
 */
public class OutMessage extends Thread {

    Scanner UserMesSc = new Scanner(System.in);
    Message message;
    Socket soc;

    public OutMessage(Socket soc){
        this.soc = soc;
    }

    public void run(){
        try{
            while (true) {
                message = new Message(null, UserMesSc.nextLine(), null);
                ObjectOutputStream outStream = new ObjectOutputStream(soc.getOutputStream());
                outStream.writeObject(message);
                outStream.flush();
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }
}
