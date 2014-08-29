package server;

import common.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
