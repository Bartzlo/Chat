package server;

import common.InOutMessage;
import common.Message;
import common.PrintOut;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static server.ServerChat.storage;

/**
 * Created by as2 on 28.08.2014.
 */

/*
Кароч немного по другому надо, сторедж мы должны использовать
только для хранения текущих подключений
зачем заполнять таблицу несуществующими юзерами
тем более userAndCon.put(new User("IlyaOzy"),null); выдаеи Null pointer
набор методов стореджа по моему должен быть таким:

-GetUser по сокету и по имени
-GetUserName
-GetUserSocket
-getNumberUsersConnect
-getIterator
-addConnection
-delConnection


то есть получаем пользователей их имена сокеты и тд
добавляем и удаляем подключения (пара User + UserConnect)
 */




public class Storage {
    //сначала идет стандартная реализация паттерна singleton (одиночка)

    private static Storage instance = new Storage();

    private Storage(){      //приватный конструктор нужен для того чтобы нельзя было создать объект
    }

    public static Storage getInstance(){
        //fillUserInfo(); //заполняем массив
        //пока отключил, вылетает искльчение NullPointerException (Storage.java:59)

        return instance;
    }

    private static ConcurrentHashMap<User, UserConnect> userAndCon = new ConcurrentHashMap<User, UserConnect>();
    // Concurrent - безопасный для использования нескольких потоков

    private static ConcurrentHashMap<User, Integer> Users = new ConcurrentHashMap<User, Integer>();


    private static void fillUserInfo(){
        //по идее тут должно быть считывание с файла, но пока путь будет так
        Users.put(new User("IlyaOzy"),null); //у неподключенных пользователей второе значение будет null
        Users.put(new User("BartZlo"),null); //у неподключенных пользователей второе значение будет null
        Users.put(new User("Evgenia"),null); //у неподключенных пользователей второе значение будет null
        Users.put(new User("Admin"),null); //у неподключенных пользователей второе значение будет null

        //у неавторизованных пользователей имя будет "Гость345"  - я так предлагаю. Без имени использовать HashMap не выйдет
    }

    public int LogInUser(User user, String name, String pass){ //пароль пока не используем
        for (Map.Entry<User, Integer> ob1 : Users.entrySet()) {
            if (ob1.getKey().getName().equals(name)){
                user = ob1.getKey();
                return 0;
            }
        }
        return -1;
    }

    public int RegistrationUser(User user, String name, String pass){ //пароль пока не используем
        for (Map.Entry<User, Integer> ob1 : Users.entrySet()) {
            if (ob1.getKey().getName().equals(name)){
                return -1;
            }
        }
        Users.put(user, null);
        return 0;
    }

    public void addConnection (User user, UserConnect userConnect) {
        boolean isThere = false;
        for (Map.Entry<User, UserConnect> ob1 : userAndCon.entrySet()) {
            if (ob1.getKey().equals(user)){
                isThere = true;
            }
        }
        if (!isThere){
            userAndCon.put(user, userConnect);
        }
    }

    public void delConnection (User user) {
        userAndCon.remove(user);
    }

    public User GetUser(Socket soc){
        for (Map.Entry<User, UserConnect> ob1 : userAndCon.entrySet()) {
            if (ob1.getValue().getUserSoc().equals(soc)){
                return ob1.getKey();
            }
        }
        return null;
    }

    public User GetUser(String name){
        //return new User();
        for (Map.Entry<User, UserConnect> ob1 : userAndCon.entrySet()) {
            if (ob1.getKey().getName().equals(name)){
                return ob1.getKey();
            }
        }
        return null;
    }

//    public String GetUserName(Socket soc){
//        return new String();
//    }

    public UserConnect GetUserConnect(String name){
        //return new Socket();
        for (Map.Entry<User, UserConnect> ob1 : userAndCon.entrySet()) {
            if (ob1.getKey().getName().equals(name)){
                return ob1.getValue();
            }

        }
        return null;
    }

    public UserConnect GetUserConnect(User user){
        //return new Socket();
        for (Map.Entry<User, UserConnect> ob1 : userAndCon.entrySet()) {
            if (ob1.getKey().getName().equals(user.getName())){
                return ob1.getValue();
            }

        }
        return null;
    }

    public int getNumberUsersConnect (){    // Количество подключений
        int n=userAndCon.size();
        return n;
    }

    public Iterator getIterator (){
        Iterator<Map.Entry<User, UserConnect>> it = userAndCon.entrySet().iterator();
        return it;
    }

}
