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

   static void sendMessageAll (Message message){
       Iterator <Map.Entry<User, UserConnect>> it = storage.getIterator();
       while (it.hasNext()){
           Map.Entry<User, UserConnect> userAndCon = it.next();
           Socket soc = userAndCon.getValue().getUserSoc();
           User user = userAndCon.getValue().getUser();
           try {
               InOutMessage.sendMessage(soc, message);
           }
           catch (IOException x){
               if (x.getMessage().equals("Connection reset")){
                   storage.delConnection(user);
               } else x.printStackTrace();
           }
           catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   }

   static void sendPrivatMessage (Message mes, Socket soc) throws IOException {
       ObjectOutputStream outMes = new ObjectOutputStream(soc.getOutputStream());
       outMes.writeObject(mes);
   }

   static Message readMessage (Socket soc) {
       try {
           return InOutMessage.getMessage(soc);
       }
       catch(SocketTimeoutException x){
           return null;
       }
       catch (IOException x) {
           if (x.getMessage().equals("Socket is closed")) {
               x.printStackTrace();
               storage.delConnection(storage.GetUser(soc));
               return null;
           }if (x.getMessage().equals("Connection reset")) {
               storage.delConnection(storage.GetUser(soc));
           } else x.printStackTrace();
           return null;
       }
       catch(ClassNotFoundException e){
           e.printStackTrace();
           return null;
       }

   }


}
