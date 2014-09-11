package server;

import common.Message;
import common.PrintOut;
import javafx.util.Pair;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static server.ServerChat.storage;

/**
 * Created by Bart on 29.08.2014.
 */
public class DecodeReader {
    Message message;


    interface DecodeR{
        void decode(Message message)throws IOException, InterruptedException;
    }

    private static HashMap<Pair<String, String>, DecodeR> Chosing = new HashMap<Pair<String, String>, DecodeR>();

    protected  class Registration1 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {
            Message mes = new Message("System","Enter new login",new Date());
            Socket soc = storage.getUserSoc(message.getUserName());
            Messager.sendPrivatMessage(mes, soc);
            storage.getUser(message.getUserName()).setStage("RegLogin");
        }
    }

    protected  class Registration2 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {

            Socket soc = storage.getUserSoc(message.getUserName());

            if (!Log.checUserName(message.getMessage())){
                Message mes2 = new Message("System","This name is already taken. Enter new login",new Date());
                Messager.sendPrivatMessage(mes2, soc);
                return;
            }

            if ((storage.getUser(message.getMessage()) != null) &
               !(message.getUserName().equals(message.getMessage()))){
                    Message mes2 = new Message("System","This user is already chatting.",new Date());
                    Messager.sendPrivatMessage(mes2, soc);
                    return;
            }

            Message mes1 = new Message("System","Enter new password",new Date());
            Messager.sendPrivatMessage(mes1, soc);
            storage.getUser(message.getUserName()).setStage("RegPass");
            storage.getUser(message.getUserName()).setName(message.getMessage());
        }
    }

    protected  class Registration3 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {
            Message mes = new Message("System","Are you registered!",new Date());
            Socket soc = storage.getUserSoc(message.getUserName());
            storage.getUser(message.getUserName()).setStage("Registered");
            Log.addUserReg(message.getUserName(),message.getMessage());
            Messager.sendLastLog(storage.getUser(message.getUserName()));
            Messager.sendPrivatMessage(mes, soc);
            Messager.sendMessageAll(new Message("System", "Connect: " + message.getUserName(), new Date()));
        }
    }



    protected  class Login1 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {
            Message mes = new Message("System","Enter you login",new Date());
            Socket soc = storage.getUserSoc(message.getUserName());
            Messager.sendPrivatMessage(mes, soc);
            storage.getUser(message.getUserName()).setStage("Login");
        }
    }

    protected  class Login2 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {
            Socket soc = storage.getUserSoc(message.getUserName());

            if (Log.checUserName(message.getMessage())){
                Message mes2 = new Message("System","User not found. For registration, enter: /reg",new Date());
                Messager.sendPrivatMessage(mes2, soc);
                return;
            }

            if ((storage.getUser(message.getMessage()) != null) &
               !(message.getUserName().equals(message.getMessage()))){
                    Message mes2 = new Message("System","This user is already chatting. For registration, enter: /reg",new Date());
                    Messager.sendPrivatMessage(mes2, soc);
                    return;
            }

            Message mes1 = new Message("System","Enter you password",new Date());
            Messager.sendPrivatMessage(mes1, soc);
            storage.getUser(message.getUserName()).setStage("LoginPass");
            storage.getUser(message.getUserName()).setName(message.getMessage());
        }
    }

    protected  class Login3 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {

            Socket soc = storage.getUserSoc(message.getUserName());

            if (Log.checUserPass(message.getUserName(), message.getMessage())){
                Messager.sendLastLog(storage.getUser(message.getUserName()));
                storage.getUser(message.getUserName()).setStage("Registered");
                Messager.sendMessageAll(new Message("System", "Connect: " + message.getUserName(), new Date()));
            }else {
                Message mes2 = new Message("System","Wrong password. For registration, enter: /reg",new Date());
                Messager.sendPrivatMessage(mes2, soc);
            }


        }
    }


    protected  class Registered implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {
            Log.wrireUserLog(message);
            PrintOut.printMessage(message);
            Messager.sendMessageAll(message);
        }
    }

    protected  class LogOut implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException {
            Socket soc = storage.getUserSoc(message.getUserName());
            Message mes = new Message("System", "You have been signed out of chat", new Date());
            Messager.sendPrivatMessage(mes,soc);
            storage.getUser(message.getUserName()).setStage("Guest");
            Messager.sendMessageAll(new Message("System", "Disconnect: " + message.getUserName(), new Date()));
        }
    }


    protected  class Decoder1 implements DecodeR{
        @Override
        public void decode(Message message) throws IOException, InterruptedException{
            Message mes = new Message("System", "You do not Authorizing. " +
                    "For registration enter /reg. For for authorization enter /login", new Date());
            Socket soc = storage.getUserSoc(message.getUserName());
            Messager.sendPrivatMessage(mes,soc);
        }
    }



    public DecodeReader(Message message) throws IOException, InterruptedException {
        Chosing.put(new Pair("Guest",null ), new Decoder1());

        Chosing.put(new Pair("Guest","/reg" ), new Registration1());
        Chosing.put(new Pair("Login","/reg" ), new Registration1());
        Chosing.put(new Pair("LoginPass","/reg" ), new Registration1());
        Chosing.put(new Pair("RegLogin","/reg" ), new Registration1());
        Chosing.put(new Pair("RegPass","/reg" ), new Registration1());
        Chosing.put(new Pair("RegLogin",null ), new Registration2());
        Chosing.put(new Pair("RegPass",null ), new Registration3());

        Chosing.put(new Pair("Guest","/login" ), new Login1());
        Chosing.put(new Pair("RegLogin","/login" ), new Login1());
        Chosing.put(new Pair("RegPass","/login" ), new Login1());
        Chosing.put(new Pair("Login","/login" ), new Login1());
        Chosing.put(new Pair("LoginPass","/login" ), new Login1());
        Chosing.put(new Pair("Login",null ), new Login2());
        Chosing.put(new Pair("LoginPass",null ), new Login3());

        Chosing.put(new Pair("Registered",null ), new Registered());
        Chosing.put(new Pair("Registered","/exit" ), new LogOut());


        this.message = message;
        this.run();
    }

    //********** Код для парсинга сообщения **********************************
    //********** на выходе чистое сообщение и строка команды *****************
    // ********* если команды нет то String com = null ***********************
    //********** можешь потестить в отдельном файле **************************
    private String getCommand (){
        String com=null, arg=null;
        String mes = message.getMessage();             // mes это сообщение из объекта Message
        mes = mes.replaceAll("^\\s+ | \\s+$","");   // убираем пробелы в начале и в конце

        Pattern comPat = Pattern.compile("(^/{1}\\w)");
        Matcher comMat = comPat.matcher(mes);

        if (comMat.find()){                                            // если есть слово в начале с одним символом '/'
            com = mes.split("\\s+")[0];                             // то это команда
            mes = mes.replaceAll(com,"").replaceAll("^\\s+",""); // вырезаем её из сообщения
        }

        message.setMessage(mes);
        return com;
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
//        User user = storage.getUser(message.getUserName());
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

        // с парсером для работы чудо мапы нужно только это
        User user = storage.getUser(message.getUserName());
        Pair<String, String> gg = new Pair<String, String>(user.getStage(),getCommand());
        DecodeR decoder = Chosing.get(gg);
        if (decoder == null) {
            Socket soc = storage.getUserSoc(user);
            Message mes1 = new Message("System","Invalid input!",new Date());
            Messager.sendPrivatMessage(mes1, soc);
            return;
        }
        decoder.decode(message);
        PrintOut.printMessage(message);
    }
}
