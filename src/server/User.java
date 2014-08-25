package server;

import java.net.ServerSocket;
import java.net.Socket;
import static server.ServerChat.*;

/**
 * Created by Bart on 25.08.2014.
 */
public class User {
    public String userName;
    public Socket userSoc;
    boolean user = false;

    User (ServerSocket ss){
        try {userSoc = ss.accept(); }catch (Exception x){}
        userName = "User number "+ (socList.size()+1);
    }

    public boolean checkUser(){
        return user;
    }


}
