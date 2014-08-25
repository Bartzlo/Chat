package server;

import java.net.ServerSocket;
import static server.ServerChat.*;


// Поиск новых подключений
public class Connect implements Runnable{

    public void run(){
        try {

            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Server is ready");

            while (true){
                socList.add(new User(ss));

                //Если пользователя создать не удалось удаляем только что созданный
                //элемент из socList'а, и переходим к следующей интерации
                if (!socList.get(socList.size()-1).checkUser()){
                    System.out.println ("Unsuccessful connection user");
                    socList.remove(socList.size()-1);
                    continue;
                }

                System.out.println("Connect to "+ socList.get(socList.size()-1).userSoc);
                socList.get(socList.size()-1).userSoc.setSoTimeout(1);
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }
}
