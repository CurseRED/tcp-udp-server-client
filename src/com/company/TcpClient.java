package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Random;

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
        byte[] data = new byte[1024];
        Random random = new Random();
        random.nextBytes(data);
        long startTime = System.nanoTime();
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            for (int i = 0; i < 128; i++) {
                outputStream.write(data);
                System.out.println("Sending 128 packets: " + (1 + i) + "\\128");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        float count = 0;
        try {
            System.out.println("Waiting for response...");
            InputStream inputStream = clientSocket.getInputStream();
            count = (float)inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Transfer speed is " + ((count)/((float)(endTime - startTime)/1000000000)) + "kB/s");
        System.out.println("Packets received: " + (100*count/128) + "%");
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
