package server;

import common.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Random;

import static server.ServerChat.storage;

/**
 * Created by Bart on 29.08.2014.
 */
public class Autorizator implements Runnable {
    Random rn = new Random();
    private Socket soc;
    private Boolean isUserCreate = false;
    private Message logi, pass;

    public Autorizator (Socket soc) throws SocketException {
        this.soc = soc;
        this.soc.setSoTimeout(1);
    }

    public void run(){

        User unResistUser = new User("Guest_" + rn.nextInt());
        UserConnect unResistUserCon = new UserConnect(unResistUser, soc);
        try {
            Messager.sendMessageAll(new Message("SERVER", "Connect: " + unResistUser.getName(), new Date()));
            Messager.sendPrivatMessage(new Message("SERVER", "Welcome " + unResistUser.getName(), new Date()), soc);
            System.out.println("Connect: " + unResistUser.getName() + " | " + soc.toString() + " | " + new Date().toString());
            storage.addConnection(unResistUser, unResistUserCon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Пока же без регистрации

        /*Message mesLogin = new Message("Server", "Please enter your login", new Date());
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
        */
    }

}
