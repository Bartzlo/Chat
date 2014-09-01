package client;

import common.InOutMessage;
import common.Message;
import common.PrintOut;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by Bart on 25.08.2014.
 */
public class InnerMessage implements Runnable{

    public ArrayList<Message> ClientLog = new ArrayList<Message>();
    Socket soc;

    public InnerMessage(Socket soc){
        this.soc = soc;
    }

    public void run(){
        while (true){
            try {
                Message mes = InOutMessage.getMessage(soc);
               // ClientLog.add(mes);
               // PrintOut.printMessageClient(ClientLog);
                PrintOut.printMessage(mes);
            }
            catch(SocketTimeoutException x){
                continue;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
