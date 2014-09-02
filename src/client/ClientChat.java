package client;

import common.Message;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class ClientChat {

    static public Socket soc = null;

    public static void main(String[] args) {

        // Запускаем поиск сервера
        Connect.run();

        // Запускаем поток для према сообщенийи
        new Thread(new InnerMessage()).start();

        // Запускаем поток для отправки сообщений
        new Thread(new OutMessage()).start();
    }
}

