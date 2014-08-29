package server;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

import static server.ServerChat.*;

// Отправка сообщений клиентам
public class Messager{

   static void sendMessageAll (Message message){
       Iterator it = storage.getIterator();
       while (it.hasNext()){
           // Код для отправки сообщений всем
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
