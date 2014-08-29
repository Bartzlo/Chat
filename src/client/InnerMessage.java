package client;

import common.Message;
import common.PrintOut;

import java.io.ObjectInputStream;

/**
 * Created by Bart on 25.08.2014.
 */
public class InnerMessage implements Runnable{

    ObjectInputStream inStream;

    public InnerMessage(ObjectInputStream in){
        inStream = in;
    }

    public void run(){
        try{
            while (true){
                Message message = (Message) inStream.readObject();
                PrintOut.printMessasge(message);
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }


}
