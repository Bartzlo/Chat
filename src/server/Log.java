package server;

import common.Message;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Bart on 05.09.2014.
 */
public class Log {

    private static String base;
    private static String logTable = "MESSAGE";
    private static String regTable = "REGIST";

    public static void initBd(String baseName){
        base = baseName;
        // Создаем таблицу для заиси лога пользователей
        try {
            Class.forName("org.sqlite.JDBC");
            Statement st = Log.getStatement();
            String tabe = "CREATE TABLE "+ logTable +" " +
                    " (USERNAME      TEXT    NOT NULL, " +
                    " MESSAGE        TEXT    NOT NULL, " +
                    " DATE           TEXT    NOT NULL)";
            st.executeUpdate(tabe);

        } catch (SQLException e) {
            if (!e.getMessage().toString().equals("table "+ logTable +" already exists")){
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Создаем таблицу зарегистрированных
        try {
            Class.forName("org.sqlite.JDBC");
            Statement st = Log.getStatement();
            String tabe = "CREATE TABLE "+ regTable +" " +
                    " (USERNAME      TEXT    NOT NULL, " +
                    " PASS        TEXT    NOT NULL)";
            st.executeUpdate(tabe);

        } catch (SQLException e) {
            if (!e.getMessage().toString().equals("table "+ regTable +" already exists")){
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void wrireUserLog(Message mes){

        String userName = mes.getUserName();
        String message = mes.getMessage();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = dateFormat.format(mes.getDate()).toString();
        Statement st = Log.getStatement();

        try {
            String logMes = "INSERT INTO "+ logTable +" (USERNAME,MESSAGE,DATE) " +
                    "VALUES ('"+userName+"','"+message+"', '"+date+"' );";
            st.executeUpdate(logMes);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Message> getLastLog (){
        ArrayList <Message> messages = new ArrayList<Message>();
        Statement st =Log.getStatement();
        int lenght = Log.getLenght(logTable);

        try {
            ResultSet rs = st.executeQuery("SELECT * FROM MESSAGE;");

            while (rs.next()){
                if (lenght-rs.getRow()<8){
                    String userName = rs.getString("USERNAME");
                    String message = rs.getString("MESSAGE");
                    Date date = rs.getDate("DATE");
                    messages.add(new Message(userName, message, date));
                }else continue;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private static int getLenght (String table){
        int lenght;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:" + base);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) AS total from " + table);
            rs.next();
            lenght = rs.getInt("total");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            lenght = -1;
        }
        return lenght;
    }

    private static Statement getStatement (){
        Connection con = null;
        Statement st =null;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + base);
            st = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static Boolean addUserReg (String userName, String pass){
        Statement st = Log.getStatement();

        if (!Log.checUserName(userName)) return (false);

        try {
            String newUser = "INSERT INTO " + regTable + " (USERNAME,PASS) " +
                    "VALUES ('" + userName + "', '" + pass + "' );";
            st.executeUpdate(newUser);
            st.close();
            return (true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (false);
    }

    public static Boolean checUserName(String userName){
        Statement st = Log.getStatement();
        Boolean isFree = false;

        try {
            ResultSet rs = st.executeQuery("SELECT * FROM REGIST;");

            while (rs.next()){
                String user = rs.getString("USERNAME");
                if (user.equals(userName)){
                    st.close();
                    return (isFree);
                }
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        isFree = true;
        return (isFree);
    }

    public static Boolean checUserPass(String userName, String userPass){
        Statement st = Log.getStatement();
        Boolean isOkey = false;

        try {
            ResultSet rs = st.executeQuery("SELECT * FROM REGIST;");

            while (rs.next()){
                String user = rs.getString("USERNAME");
                String pass = rs.getString("PASS");
                if (user.equals(userName) & pass.equals(userPass)){
                    st.close();
                    isOkey = true;
                    return (isOkey);
                }
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (isOkey);
    }


}
