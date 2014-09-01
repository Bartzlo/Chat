package client;

import common.Message;
import common.PrintOut;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Bart on 25.08.2014.
 */
public class InnerMessage implements Runnable{

    Socket soc;

    public InnerMessage(Socket soc){
        this.soc = soc;
    }

    public void run(){
        try{
            while (true){
                ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                Message message = (Message) in.readObject();
                PrintOut.printMessasge(message);
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }


}
