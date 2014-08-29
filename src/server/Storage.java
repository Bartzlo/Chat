package server;

import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        fillUserInfo(); //заполняем массив

        return instance;
    }

    public static ConcurrentHashMap<User, UserConnect> userAndCon = new ConcurrentHashMap<User, UserConnect>();
    // Concurrent - безопасный для использования нескольких потоков

    private static void fillUserInfo(){
        //по идее тут должно быть считывание с файла, но пока путь будет так
        userAndCon.put(new User("IlyaOzy"),null); //у неподключенных пользователей второе значение будет null
        userAndCon.put(new User("BartZlo"),null); //у неподключенных пользователей второе значение будет null
        userAndCon.put(new User("Evgenia"),null); //у неподключенных пользователей второе значение будет null
        userAndCon.put(new User("Admin"),null); //у неподключенных пользователей второе значение будет null

        //у неавторизованных пользователей имя будет "Гость345"  - я так предлагаю. Без имени использовать HashMap не выйдет
    }

    public void addConnection (User user, UserConnect userConnect){
        userAndCon.put(user, userConnect);

    }

    public void delConnection (User user){
        userAndCon.remove(user);
    }

    public User GetUser(Socket soc){
        return new User();
    }

    public User GetUser(String name){
        return new User();
    }

    public String GetUserName(Socket soc){
        return new String();
    }

    public Socket GetUserSocket(String name){
        return new Socket();
    }

    public int getNumberUsersConnect (){    // Количество подключений
        int n=5;
        return n;
    }

    public Iterator getIterator (){
        Iterator<Map.Entry<User, UserConnect>> it = userAndCon.entrySet().iterator();
        return it;
    }

}
