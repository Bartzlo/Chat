package server;

import java.io.DataOutputStream;
import static server.ServerChat.*;

// Отправка сообщений клиентам
public class Messager{

    static DataOutputStream outMes;

    public static void sendMessage(int numClient, String message){
       if (socList.size() < 2) return;
        try{
            for (int i=0; i < socList.size(); i++){
                if (i == numClient) continue; //если это отправитель, ему не посылаем это сообщение
                outMes = new DataOutputStream(socList.get(i).userSoc.getOutputStream());
                outMes.writeUTF(message);
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
    }
}
