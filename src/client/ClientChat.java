package client;

import common.Message;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class ClientChat {

    public static void main(String[] args) {

        try{
            Socket soc = new Socket("127.0.0.1", 6666);
            System.out.println("Client is ready");
            soc.setSoTimeout(1);


            // Запускаем поток для према сообщенийи
            new Thread(new InnerMessage(soc)).start();

            // Запускаем поток для отправки сообщений
            new Thread(new OutMessage(soc)).start();
       }
        catch(Exception x){
            x.printStackTrace();
        }
    }
}
