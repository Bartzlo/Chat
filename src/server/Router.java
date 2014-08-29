package server;

import javafx.util.Pair;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;

import static server.ServerChat.socList;
import static server.ServerChat.storage;

// Принимаем сообщения
public class Router implements Runnable{

    public void run(){
        while (true) {
            // Если нет подключений к серверу, ждем 1 секунду
            if (storage.getNumberUsers() < 1){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException x){}
            }

            // Получаем итератор
            Iterator it = storage.getIterator();

            while (it.hasNext()){
                try{
                    Pair <User, UserConnect> userAndCon =it.next() <User, UserConnect>;
                    if (!userAndCon.getKey().checkUser()) continue; // если юзер не создан продолжаем
                    Socket soc = userAndCon.getValue().getUserSoc();
                    Message mes = Messager.readMessage(soc); // Ждем сообщения в этом же потоке
                    new Thread(new DecodeReader(mes)).start(); // Запускаем поток обработки сообщений
                }
                //Если нет сообщения в течении 1 мс идем дальше
                catch(SocketTimeoutException x){
                    continue;
                }
                //Если потерянно соединение, выводим сообщение с сервер и продолжаем
                //Удаляем этого пользователя из списа socList
                catch(SocketException x){
                    if (x.getMessage().equals("Connection reset")){
                        // Сдесь нужно удалить отметить что пользователь отключился
                    }
                }
                catch(Exception x){
                    x.printStackTrace();
                }
            }
        }
    }
}
