package client;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class ClientChat {

    public static void main(String[] args) {

        try{
            Socket soc = new Socket("127.0.0.1", 6666);
            DataOutputStream out = new DataOutputStream(soc.getOutputStream());
            DataInputStream in = new DataInputStream(soc.getInputStream());
            System.out.println("Client is ready");

            // Запускаем поток для приема сообщений
            new Thread(new InnerMessage(in)).start();

            // Запускаем поток для отправки сообщений
            new Thread(new OutMessage(out)).start();
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }
}
