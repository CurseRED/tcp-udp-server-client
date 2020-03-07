package com.company;

import java.io.*;
import java.net.Socket;

public class TcpClient implements NetworkClient{
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;

    @Override
    public void connect(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            System.out.println("Connected to server! You can type now!");
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            TcpClient.Resender resender = new TcpClient.Resender();
            resender.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String msg) {
        try {
            out.write("Client: " + msg + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFile() {

    }

    @Override
    public void getConnectionSpeed() {

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
