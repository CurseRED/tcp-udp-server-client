package com.company;

import java.io.*;
import java.net.Socket;

public class TcpClient {
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;

    public TcpClient() {
        Resender resender = new Resender();
        resender.start();
    }

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Resender extends Thread {
        private boolean isStoped = false;

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
