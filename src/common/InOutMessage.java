package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Bartzlo on 01.09.2014.
 */
public class InOutMessage {
    static public Message getMessage(Socket soc) throws IOException, ClassNotFoundException {
        ObjectInputStream inpMes = new ObjectInputStream(soc.getInputStream());
        soc.setSoTimeout(0);
        Message mes = (Message) inpMes.readObject();
        soc.setSoTimeout(1);
        return mes;
    }

    static public void sendMessage (Socket soc, Message mes) throws IOException, InterruptedException {
        ObjectOutputStream outMes = new ObjectOutputStream(soc.getOutputStream());
        outMes.writeObject(mes);
        outMes.flush();
    }
}
