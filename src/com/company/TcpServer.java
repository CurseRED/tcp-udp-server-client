package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;

    public TcpServer() {
        Resender resender = new Resender();
        resender.start();
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            System.out.println("Connection accepted!");
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        try {
            out.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Resender extends Thread {
        private boolean isStoped;

        public void setStop() {
            isStoped = true;
        }

        @Override
        public void run() {
            try {
                while (!isStoped) {
                    String str = in.readLine();
                    System.out.println(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}