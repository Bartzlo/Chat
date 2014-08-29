package server;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import static server.ServerChat.storage;

// Отправка сообщений клиентам
public class Messager{

   static void sendMessageAll (Message message){
       Iterator <Map.Entry<User, UserConnect>> it = storage.getIterator();
       while (it.hasNext()){
           try {
               Map.Entry<User, UserConnect> userAndCon = it.next();
               Socket soc = userAndCon.getValue().getUserSoc();
               User user = userAndCon.getValue().getUser();
               ObjectOutputStream outMes = new ObjectOutputStream(soc.getOutputStream());
               outMes.writeObject(message);
           } catch (IOException x){
               if (x.getMessage().equals("Connection reset")){
                   System.out.println("Disconnect" + user.getName() + new Date().toString());
                   storage.delConnection(user);
               }
           }
       }
   }

   static void sendPrivatMessage (Message mes, Socket soc) throws IOException {
       ObjectOutputStream outMes = new ObjectOutputStream(soc.getOutputStream());
       outMes.writeObject(mes);
   }

   static Message readMessage (Socket soc) throws IOException, ClassNotFoundException {
           ObjectInputStream inpMes = new ObjectInputStream(soc.getInputStream());
           Message mes = (Message) inpMes.readObject();
           return mes;
   }


}
