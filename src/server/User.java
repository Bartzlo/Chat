package server;

import java.net.ServerSocket;
import java.net.Socket;
import static server.ServerChat.*;

/**
 * Created by Bart on 25.08.2014.
 */
public class User {
    private String name;
    private String stage = "Guest";

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        System.out.println("Set stage " + this.getStage() + "-->" + stage);
        this.stage = stage;
    }

    public User(){}

    public User(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
