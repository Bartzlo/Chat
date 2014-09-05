package server;

public class ServerChat {

    // Список подключений, создание экземпляра Storage
    static Storage storage = Storage.getInstance();

    public static void main(String[] args) {

        // инициализация базы
        Log.initBd("LogMessage.db");
        //Создаем поток для поиска подключений
        new Thread(new Connect()).start();
        //Создаем поток для отслеживания сообщений
        new Thread(new Router()).start();
    }
    
}


