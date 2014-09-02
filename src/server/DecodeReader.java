package server;

import common.Message;
import common.PrintOut;

import java.io.IOException;

/**
 * Created by Bart on 29.08.2014.
 */
public class DecodeReader {
    Message message;

    public DecodeReader(Message message) throws IOException, InterruptedException {
        this.message = message;
        this.run();
    }

    private void run() throws IOException, InterruptedException {
        // Сдесь будет определятся тип сообщения, команда или просто сообщение
        // Пока в любом случае отправляем всем
        PrintOut.printMessage(message);
        Messager.sendMessageAll(message);
    }
}
