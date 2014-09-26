package client;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.Socket;

import static client.ClientChat.*;


public class Connect extends Service<Boolean>{
    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Wait, search server...");
                        con.mainArea.appendText("Wait, search server..."+"\n");
                    }
                });

                while (true) {
                    try {
                        Thread.sleep(1000);
                        soc = new Socket(IP_ADRES, PORT);
                        soc.setSoTimeout(1);
                        break;
                    } catch (IOException e) {
                        if (e.getMessage().equals("Connection refused: connect")) {
                            continue;
                        } else e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Server is found!");
                        con.mainArea.appendText("Server is found!"+"\n");
                    }
                });

                new InnerMessage().start();
                return true;
            }
        };
    }
}


/*public class Connect {
    public static void serchConnetion(){
        System.out.println("Wait, search server...");
        while (true) {
            try {
                Thread.sleep(1000);
                soc = new Socket(IP_ADRES, PORT);
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
*/