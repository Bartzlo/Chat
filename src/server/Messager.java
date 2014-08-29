package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

   static Message readMessage (Socket soc) throws IOException, ClassNotFoundException {
           ObjectInputStream inpMes = new ObjectInputStream(soc.getInputStream());
           Message message = (Message) inpMes.readObject();
           return message;
   }


}
