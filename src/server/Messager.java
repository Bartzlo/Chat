package server;

import common.InOutMessage;
import common.Message;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
           InOutMessage.sendMessage(soc, message);
       }
   }

   static void sendPrivatMessage (Message mes, Socket soc) throws IOException, InterruptedException {
       InOutMessage.sendMessage(soc, mes);
   }

   static Message readMessage (Socket soc) throws IOException, ClassNotFoundException {
       return InOutMessage.getMessage(soc);
   }
}
