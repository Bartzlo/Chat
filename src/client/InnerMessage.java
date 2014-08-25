package client;

import java.io.DataInputStream;

/**
 * Created by Bart on 25.08.2014.
 */
public class InnerMessage implements Runnable{

    DataInputStream inStream;

    InnerMessage(DataInputStream in){
        inStream = in;
    }

    public void run(){
        try{
            while (true){
                System.out.println(inStream.readUTF());
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }


}
