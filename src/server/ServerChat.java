package server;

import java.io.*;
import java.util.Properties;

public class ServerChat {

    // Список подключений, создание экземпляра Storage
    static Storage storage = Storage.getInstance();
    static private int PORT=6666;

    public static void main(String[] args) {

        Properties config = new Properties();
        File configFile = new File("configServer.ini");
        if (!configFile.isFile()){
            try {
                configFile.createNewFile();
                FileWriter fv = new FileWriter(configFile);
                fv.write("PORT=6666");
                fv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config.load(new FileInputStream((new File("configServer.ini"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PORT = Integer.valueOf(config.getProperty("PORT"));

        // инициализация базы
        Log.initBd("LogMessage.db");
        //Создаем поток для поиска подключений
        new Thread(new Connect(PORT)).start();
        //Создаем поток для отслеживания сообщений
        new Thread(new Router()).start();
    }
    
}


