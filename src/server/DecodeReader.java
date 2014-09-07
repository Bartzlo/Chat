package server;

import common.Message;
import common.PrintOut;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Bart on 29.08.2014.
 */
public class DecodeReader {
    Message message;


    interface DecodeR{
        void decode(Message message)throws IOException, InterruptedException;
    }

    private static HashMap<Pair<String, String>, DecodeR> Chosing = new HashMap<Pair<String, String>, DecodeR>();

    DecodeReader(){
        //первый идёт этап а потом команда
        Chosing.put(new Pair("Guest",null ), new Decoder1());
        Chosing.put(new Pair("Pass",null ), new Decoder2());
        Chosing.put(new Pair("Active",null ), new Decoder3()); //null означает что не совпадает ни с одной командой
        Chosing.put(new Pair("Active","\\\\logout" ), new Decoder3());
    }


    protected  class Decoder1 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException{
            Storage storage = Storage.getInstance();
            User user = storage.GetUser(message.getMessage());
            if (user != null){
                storage.setStage(user,"Pass");

            }
            else{
                Messager.sendPrivatMessage(new Message("System","That user doesn't exist, try again"
                        , new Date()),  storage.GetUserConnect(message.getUserName()).getUserSoc() );

            }


        }
    }

    protected  class Decoder2 implements DecodeR{
        @Override
        public void decode(Message message) {

        }
    }

    protected  class Decoder3 implements DecodeR{
        @Override
        public void decode(Message message) {

        }
    }

    public DecodeReader(Message message) throws IOException, InterruptedException {
        this.message = message;
        this.run();
    }

    private void run() throws IOException, InterruptedException {
        // Сдесь будет определятся тип сообщения, команда или просто сообщение
        // Пока в любом случае отправляем всем

        // какие условия надо учитывать: этап пользователя, сообщение которое он отправил (совпадение с определенным кодом)
        // этапы пользователей: гость, пароль, вводпароля (при создании нового пользователя), поддтверждение пароля,
        // активен (при создании нового пользователя), админ, забанен =) хехехе.
        // команды: //create создание нового пользователя (этапы любые кроме забанен)

        // команды: //login перейти к вводу существующего логина (этапы гость)
        // команды: //repass смена пароля (этапы активен, админ)

//        Storage storage = Storage.getInstance();
//
//        User user = storage.GetUser(message.getUserName());
//
//        if (user != null){
//            Pair<String, String> gg = new Pair<String, String>(user.getStage(),message.getMessage());
//
//            DecodeR decoder = Chosing.get(gg);
//
//            if (decoder == null){
//                 gg = new Pair<String, String>(user.getStage(),null);
//                decoder = Chosing.get(gg);
//                if (decoder == null)
//                {
//                    System.out.println("DecodeReader: Combination doesn't exist");
//                }
//                else {
//                    decoder.decode(message);
//                    //срабатывает если в строке команды нет, null обозначение смысловое
//                }
//
//            }else {
//                decoder.decode(message);
//                //срабатывает если в строке какая то команда
//            }
//
//
//        }

        Log.wrireUserLog(message);
        PrintOut.printMessage(message);
        Messager.sendMessageAll(message);
    }
}
