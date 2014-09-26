package client;

import common.InOutMessage;
import common.Message;
import javafx.concurrent.Task;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static client.ClientChat.soc;

/**
 * Created by Bart on 25.08.2014.
 */

public class OutMessage extends Task<Void>{

    String textMes;
    Message message;

    public OutMessage(String textMes) {
        this.textMes = textMes;
    }

    @Override
    protected Void call() throws Exception {
        try {
            message = new Message(null, textMes, null);
            InOutMessage.sendMessage(soc, message);
        } catch (IOException e) {
            if (e.getMessage().equals("Connection reset by peer: socket write error")){
               // continue;
            } else e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            if (e.getMessage().equals("No line found")){
              //  continue;
            } else e.printStackTrace();
        }
        return null;
    }
}



/*
public class OutMessage extends Thread {

    Scanner userMesSc = new Scanner(System.in);
    Message message;

    public void run(){
        while (true){
            try {
                message = new Message(null, userMesSc.nextLine(), null);
                InOutMessage.sendMessage(soc, message);
            } catch (IOException e) {
                if (e.getMessage().equals("Connection reset by peer: socket write error")){
                    continue;
                } else e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                if (e.getMessage().equals("No line found")){
                    continue;
                } else e.printStackTrace();
            }
        }
    }
}*/
