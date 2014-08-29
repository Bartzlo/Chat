package server;

import java.util.ArrayList;


public class ServerChat {

    // Список подключений    
    static Storage storage;  // статик для того чтобы я мог обращатся к нему из других классов

    public static void main(String[] args) {

        //создаем экземпляр Storage
        Storage storage = Storage.getInstance();
        //Создаем поток для поиска подключений
        new Thread(new Connect()).start();
        //Создаем поток для отслеживания сообщений
        new Thread(new Router()).start();
    }
    
}


