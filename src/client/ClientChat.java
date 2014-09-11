package client;

import common.Message;

import java.net.*;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;


public class ClientChat {

    static public Socket soc = null;
    static public String IP_ADRES="127.0.0.1";
    static public int PORT=6666;

    public static void main(String[] args) {

        Properties config = new Properties();
        File configFile = new File("config.ini");
        if (!configFile.isFile()){
            try {
                configFile.createNewFile();
                FileWriter fv = new FileWriter(configFile);
                fv.write("IP_ADRES=127.0.0.1" + "\n");
                fv.write("PORT=6666");
                fv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config.load(new FileInputStream((new File("config.ini"))));
        } catch (FileNotFoundException e){
            new File("config.ini");
        } catch (IOException e) {
            e.printStackTrace();
        }

        IP_ADRES = config.getProperty("IP_ADRES");
        PORT = Integer.valueOf(config.getProperty("PORT"));


        // Запускаем поиск сервера
        Connect.run();

        // Запускаем поток для према сообщенийи
        new Thread(new InnerMessage()).start();

        // Запускаем поток для отправки сообщений
        new Thread(new OutMessage()).start();
    }
}

