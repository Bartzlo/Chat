package server;

public class ServerChat {

    // Список подключений, создание экземпляра Storage
    static Storage storage = Storage.getInstance();  // статик для того чтобы я мог обращатся к нему из других классов

    public static void main(String[] args) {
        //Создаем поток для поиска подключений
        new Thread(new Connect()).start();
        //Создаем поток для отслеживания сообщений
        new Thread(new Router()).start();
    }
    
}


