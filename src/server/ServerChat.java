package server;

import java.util.ArrayList;


public class ServerChat {
    
    // Список подключений    
    static public ArrayList<User> socList = new ArrayList<User>();

    public static void main(String[] args) {
     
        //Создаем поток для поиска подключений
        new Thread(new Connect()).start();
        new Thread(new Router()).start();
        //Test GIT
    }
    
}

