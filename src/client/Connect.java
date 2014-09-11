package client;

import java.io.IOException;
import java.net.Socket;
import static client.ClientChat.soc;

/**
 * Created by Bart on 02.09.2014.
 */
public class Connect {
    public static void run(){
        System.out.println("Wait, search server...");
        while (true) {
            try {
                Thread.sleep(1000);
                soc = new Socket("192.168.35.50", 6666);
                soc.setSoTimeout(1);
                System.out.println("Server is found!");
                break;
            } catch (IOException e) {
                if (e.getMessage().equals("Connection refused: connect")) {
                    continue;
                } else e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
