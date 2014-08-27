package server;

import java.net.Socket;

/**
 * Created by as2 on 28.08.2014.
 */
public class UserConnect {
    private User user;
    private Socket userSoc;

    public User getUser() {
        return user;
    }

    public Socket getUserSoc() {
        return userSoc;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserSoc(Socket userSoc) {
        this.userSoc = userSoc;
    }
}
