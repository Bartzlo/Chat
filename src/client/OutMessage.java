package client;

import common.InOutMessage;
import common.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Bart on 25.08.2014.
 */
public class OutMessage extends Thread {

    Scanner userMesSc = new Scanner(System.in);
    Message message;
    Socket soc;

    public OutMessage(Socket soc){
        this.soc = soc;
    }

    public void run(){
        while (true){
            message = new Message(null, userMesSc.nextLine(), null);
            try {
                InOutMessage.sendMessage(soc, message);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
