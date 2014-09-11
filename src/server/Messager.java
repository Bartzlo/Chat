package server;

import common.InOutMessage;
import common.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import static server.ServerChat.storage;

// Отправка сообщений клиентам
public class Messager{

   static void sendMessageAll (Message message) throws IOException, InterruptedException {
       Iterator <Map.Entry<User, UserConnect>> it = storage.getIterator();
       while (it.hasNext()){
           Map.Entry<User, UserConnect> userAndCon = it.next();
           Socket soc = userAndCon.getValue().getUserSoc();
           User user = userAndCon.getValue().getUser();
           if (user.getStage().equals("Registered")) {
               InOutMessage.sendMessage(soc, message);
           }
       }
   }

    static void sendLastLog (User user) throws IOException, InterruptedException {
        ArrayList<Message> messages = Log.getLastLog();
        Iterator <Message> it = messages.iterator();
        Socket soc = storage.getUserConnect(user).getUserSoc();
        while (it.hasNext()){
            InOutMessage.sendMessage(soc, it.next());
        }
    }


   static void sendPrivatMessage (Message mes, Socket soc) throws IOException, InterruptedException {
       InOutMessage.sendMessage(soc, mes);
   }

   static Message readMessage (Socket soc) throws IOException, ClassNotFoundException {
       return InOutMessage.getMessage(soc);
   }
}
