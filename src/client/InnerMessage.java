package client;

import server.Message;
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
                String date = message.getDate().toString();
                String mesStr = message.getMessage();
                String name = message.getUserName();
                System.out.println(date + " | " + name + ": " + mesStr);
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }


}
