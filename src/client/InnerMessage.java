package client;

import common.Message;
import common.PrintOut;

import java.io.ObjectInputStream;
import java.net.Socket;
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
        try{
            while (true){
                ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                Message message = (Message) in.readObject();
                ClientLog.add(message);
                PrintOut.printMessageClient(ClientLog);
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }


}
