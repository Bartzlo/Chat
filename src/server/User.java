package server;

import java.net.ServerSocket;
import java.net.Socket;
import static server.ServerChat.*;

/**
 * Created by Bart on 25.08.2014.
 */
public class User {
    private String name;
    //public Socket userSoc;
    private boolean isUserCreated = true;

    public User(){
    }

    public User(String name){
        name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    User (ServerSocket ss){
//        try {userSoc = ss.accept(); }
//        catch (Exception x){}
//        userName = "User number "+ (socList.size()+1);
//    }

    public boolean checkUser(){
        return isUserCreated;
    }


}
