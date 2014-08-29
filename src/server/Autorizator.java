package server;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Bart on 29.08.2014.
 */
public class Autorizator implements Runnable {
    private Socket soc;
    private Boolean isUserCreate = false;

    public Autorizator (Socket soc) throws SocketException {
        this.soc = soc;
        soc.setSoTimeout(1);
    }

    public void run(){
        //  Основная функция авторизатора
    }

}
