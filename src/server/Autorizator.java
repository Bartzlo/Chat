package server;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

/**
 * Created by Bart on 29.08.2014.
 */
public class Autorizator implements Runnable {
    private Socket soc;
    private Boolean isUserCreate = false;
    private Message logi, pass;

    public Autorizator (Socket soc) throws SocketException {
        this.soc = soc;
        this.soc.setSoTimeout(1);
    }

    public void run(){
        Message mesLogin = new Message("Server", "Please enter your login", new Date());
        Message mesPass = new Message("Server", "Please enter your password", new Date());
        try {
            Messager.sendPrivatMessage(mesLogin, soc);
            logi = Messager.readMessage(soc);
            Messager.sendPrivatMessage(mesPass, soc);
            pass = Messager.readMessage(soc);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
