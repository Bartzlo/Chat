package server;

import java.net.ServerSocket;
import static server.ServerChat.*;


// Поиск новых подключений
public class Connect implements Runnable{

    public void run(){
        try {
            ServerSocket ss = new ServerSocket(6666);
            System.out.println("Server is ready");

            // цикл поиска подключений
            while (true){
                // как только будет подключение запустится отдельный поток авторизатор
                new Thread(new Autorizator(ss.accept())).start();
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }
}
