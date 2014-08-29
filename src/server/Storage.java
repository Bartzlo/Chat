package server;

import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by as2 on 28.08.2014.
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

    public User SerachUser(Socket soc){
        return new User();
    }

    public User SerachUser(String name){
        return new User();
    }

    public String SerachUserName(Socket soc){
        return new String();
    }

    public Socket SerachUserSocket(String name){
        return new Socket();
    }

    public int getNumberUsers (){    // Количество пользователей
        int n=5;
        return n;
    }

    public Iterator getIterator (){
        Iterator it = userAndCon.entrySet().iterator();
        return it;
    }

}
