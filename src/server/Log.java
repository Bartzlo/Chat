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
    private static String mesTable = "MESSAGE";

    public static void initBd(String baseName){

        base = baseName;

        try {
            Class.forName("org.sqlite.JDBC");
            Statement st = Log.getStatement();
            String tabe = "CREATE TABLE "+ mesTable +" " +
                    " (USERNAME      TEXT    NOT NULL, " +
                    " MESSAGE        TEXT    NOT NULL, " +
                    " DATE           TEXT    NOT NULL)";
            st.executeUpdate(tabe);

        } catch (SQLException e) {
            if (!e.getMessage().toString().equals("table "+ mesTable +" already exists")){
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
            String logMes = "INSERT INTO "+ mesTable +" (USERNAME,MESSAGE,DATE) " +
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
        int lenght = Log.getLenght();

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

    private static int getLenght (){
        int lenght;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:" + base);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) AS total from " + mesTable);
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


}
