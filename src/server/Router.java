package server;

import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import static server.ServerChat.storage;

// Принимаем сообщения
public class Router implements Runnable{

    public void run(){

        while (true) {
            // Если нет подключений к серверу, ждем 1 секунду
            if (storage.getNumberUsersConnect() < 1){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException x){}
            }

            // Получаем итератор
            Iterator <Map.Entry<User, UserConnect>> it = storage.getIterator();

            while (it.hasNext()){
                try{
                    Map.Entry <User, UserConnect> userAndCon = it.next();
                    User user = userAndCon.getKey();
                    Socket soc = userAndCon.getValue().getUserSoc();
                    Message mes = Messager.readMessage(soc); // Ждем сообщения в этом же потоке

                    new Thread(new DecodeReader(mes)).start(); // Запускаем поток обработки сообщений
                }
                //Если нет сообщения в течении 1 мс идем дальше
                catch(SocketTimeoutException x){
                    continue;
                }
                //Если потерянно соединение, выводим сообщение на сервер и
                //Удаляем этого пользователя из списа storage
                catch(SocketException x){
                    if (x.getMessage().equals("Connection reset")){
                        System.out.println("Disconnect" + user.getName() + new Date().toString());
                        storage.delConnection(user);
                }
                }
                catch(Exception x){
                    x.printStackTrace();
                }
            }
        }
    }
}
