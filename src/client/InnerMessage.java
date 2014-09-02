package client;

import common.InOutMessage;
import common.Message;
import common.PrintOut;

import java.io.IOException;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static client.ClientChat.soc;

/**
 * Created by Bart on 25.08.2014.
 */
public class InnerMessage implements Runnable{

    public ArrayList<Message> ClientLog = new ArrayList<Message>();

    public void run(){
        while (true){
            try {
                Message mes = InOutMessage.getMessage(soc);
                ClientLog.add(mes);
                PrintOut.printMessageClient(ClientLog);
            } catch(SocketTimeoutException x){
                continue;
            } catch (IOException e) {
                if (e.getMessage().equals("Connection reset")){
                    System.out.println("The connection to the server is lost.");
                    Connect.run();
                    continue;
                } else e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
