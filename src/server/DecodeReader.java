package server;

import common.Message;

/**
 * Created by Bart on 29.08.2014.
 */
public class DecodeReader implements Runnable {
    Message message;

    public DecodeReader(Message message){
        this.message = message;
    }

    public void run() {
        // Сдесь будет определятся тип сообщения, команда или просто сообщение
        // Пока в любом случае отправляем всем
        Messager.sendMessageAll(message);
    }
}
