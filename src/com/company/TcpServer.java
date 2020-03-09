package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer implements NetworkServer{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connection accepted! You can type now!");
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
    public void stop() {
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String msg) {
        try {
            out.write("Server: " + msg + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(fileInputStream.readAllBytes());
            outputStream.flush();
            System.out.println("File sent successfully!");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFile(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = clientSocket.getInputStream();
            fileOutputStream.write(inputStream.readAllBytes());
            System.out.println("File received successfully!");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getConnectionSpeed() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            System.out.println("Receiving packets...");
            int result = inputStream.read();
            int counter = 0;
            byte[] data = new byte[1024];
            while (result != -1 && counter < 128) {
                result = inputStream.read(data);
                counter++;
                System.out.println("Received packets: " + counter + "\\128");
            }
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(counter);
            System.out.println("Response sent");
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