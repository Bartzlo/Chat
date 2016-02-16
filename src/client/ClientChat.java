package client;

import common.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.*;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;


public class ClientChat extends Application {

    static public Socket soc = null;
    static public String IP_ADRES="127.0.0.1";
    static public int PORT=6666;
    static public Controller con;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Properties config = new Properties();
        File configFile = new File("configClient.ini");
        if (!configFile.isFile()){
            try {
                configFile.createNewFile();
                FileWriter fv = new FileWriter(configFile);
                fv.write("IP_ADRES=127.0.0.1" + " \r\n");
                fv.write("PORT=6666");
                fv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config.load(new FileInputStream((new File("configClient.ini"))));
        } catch (FileNotFoundException e){
            new File("configClient.ini");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(config.getProperty("IP_ADRES"));
        IP_ADRES = config.getProperty("IP_ADRES");
        System.out.println(config.getProperty("PORT"));
        PORT = Integer.valueOf(config.getProperty("PORT"));


        // Запускаем поток для према сообщенийи
        //new Thread(new InnerMessage()).start();

        // Запускаем поток для отправки сообщений
       // new Thread(new OutMessage()).start();



        // создаем лоадер из fxml файла, их можно сделать несколько (разные окна)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        // создаем базовый класс
        Parent root = loader.load();
        // создаем контроллер
        con = loader.getController();
        // настраиваем и показываем окно
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}

