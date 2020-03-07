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
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connected to server! You can type now!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
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
    public void sendFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            bufferedOutputStream.write(fileInputStream.readAllBytes());
            if (in.readLine().equalsIgnoreCase("File received!")) {
                System.out.println("File was successfully sent!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startChat() {
        Resender resender = new Resender();
        resender.start();
    }

    @Override
    public void getFile(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = clientSocket.getInputStream();
            fileOutputStream.write(inputStream.readAllBytes());
            System.out.println("File received successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
