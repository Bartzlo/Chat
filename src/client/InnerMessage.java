package client;

import common.InOutMessage;
import common.Message;
import common.PrintOut;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static client.ClientChat.con;
import static client.ClientChat.soc;



public class InnerMessage extends Service<Void>{

    public ArrayList<Message> ClientLog = new ArrayList<Message>();

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true){
                    try {
                        final Message mes = InOutMessage.getMessage(soc);
                        ClientLog.add(mes);
                        PrintOut.printMessageClient(ClientLog);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                con.mainArea.appendText(mes.printMessage()+"\n");
                            }
                        });


                    } catch (NullPointerException e){
                        continue;
                    } catch(SocketTimeoutException x){
                        continue;
                    } catch (IOException e) {
                        if (e.getMessage().equals("Connection reset")){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    con.mainArea.appendText("The connection to the server is lost."+"\n");
                                    con.enterArea.setDisable(true);
                                }
                            });
                            new Connect().start();
                            break;
                        } else e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
    }
}

/*
public class InnerMessage implements Runnable{

    public ArrayList<Message> ClientLog = new ArrayList<Message>();

    public void run(){
        while (true){
            try {
                Message mes = InOutMessage.getMessage(soc);
                ClientLog.add(mes);
                PrintOut.printMessageClient(ClientLog);
            } catch (NullPointerException e){
                continue;
            } catch(SocketTimeoutException x){
                continue;
            } catch (IOException e) {
                if (e.getMessage().equals("Connection reset")){
                    System.out.println("The connection to the server is lost.");
                    //Connect.run();
                    continue;
                } else e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
*/