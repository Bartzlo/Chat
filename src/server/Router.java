package server;

import common.Message;

import java.io.IOException;
import java.net.Socket;
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
            if (storage.getNumberUsersConnect() < 1) try {
                Thread.sleep(1000);
            } catch (InterruptedException x) {
            }

            // Получаем итератор
            Iterator <Map.Entry<User, UserConnect>> it = storage.getIterator();

            while (it.hasNext()){
                    Map.Entry <User, UserConnect> userAndCon = it.next();
                    User user = userAndCon.getKey();
                    Socket soc = userAndCon.getValue().getUserSoc();
                    Message mes = Messager.readMessage(soc); // Ждем сообщения в этом же потоке
                    if (mes == null) continue;
                    mes.setDate(new Date());
                    mes.setUserName(user.getName());
                    new DecodeReader(mes); // Запускаем обработку сообщений
            }
        }
    }
}
