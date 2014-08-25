package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import static server.ServerChat.*;

// Принимаем сообщения
public class Router implements Runnable{

    DataInputStream inpMes;
    String message;

    public void run(){
        while (true) {
            // Если нет подключений к серверу, ждем 1 секунду
            if (socList.size() < 1){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException x){}
            }

            for (int i=0; i< socList.size(); i++){
                try{
                    inpMes = new DataInputStream(socList.get(i).userSoc.getInputStream());
                    message = socList.get(i).userName+": " + inpMes.readUTF();
                    Messager.sendMessage(i, message);
                }
                //Если нет сообщения в течении 1 мс идем дальше
                catch(SocketTimeoutException x){
                    continue;
                }
                //Если потерянно соединение, выводим сообщение с сервер и продолжаем
                //Удаляем этого пользователя из списа socList
                catch(SocketException x){
                    if (x.getMessage().equals("Connection reset")){
                        System.out.println("Disconnect user: "+ socList.get(i).toString());
                        socList.remove(i);
                    }
                }
                catch(IOException x){
                    x.printStackTrace();
                }
            }
        }
    }
}
