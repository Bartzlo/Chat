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

        Socket soc = null;

        try {
            while (true) {
                // Если нет подключений к серверу, ждем 1 секунду
                if (storage.getNumberUsersConnect() < 1) Thread.sleep(1000);
                // Получаем итератор
                Iterator<Map.Entry<User, UserConnect>> it = storage.getIterator();

                // Цикл по все подключенным пользователям
                while (it.hasNext()) {
                    Map.Entry<User, UserConnect> userAndCon = it.next();
                    User user = userAndCon.getKey();
                    soc = userAndCon.getValue().getUserSoc();
                    System.out.println("--------1");
                    Message mes = Messager.readMessage(soc); // Ждем сообщения в этом же потоке
                    System.out.println("--------2");
                    if (mes == null) continue;
                    mes.setDate(new Date());
                    mes.setUserName(user.getName());
                    new DecodeReader(mes); // Запускаем обработку сообщений
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(SocketTimeoutException x){
            System.out.println("Мы тут");
        } catch (IOException x) {
            if (x.getMessage().equals("Socket is closed")) {
                storage.delConnection(storage.GetUser(soc));
            } if (x.getMessage().equals("Connection reset")) {
                storage.delConnection(storage.GetUser(soc));
            } else x.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
