package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class UdpClient implements NetworkClient{
    private byte[] buffer;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private Resender resender;
    private String ip;
    private int port;

    @Override
    public void connect(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            buffer = new byte[512];
            datagramSocket = new DatagramSocket();
            System.out.println("Client created!");
            sendMessage("Connect");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startChat() {
        resender = new Resender();
        resender.start();
    }

    @Override
    public void disconnect() {
        resender.setStop();
    }

    @Override
    public void sendMessage(String msg) {
        try {
            datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(ip), port);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = fileInputStream.readAllBytes();
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            datagramSocket.send(datagramPacket);
            System.out.println("File was successfully sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFile(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);
            fileOutputStream.write(buffer);
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
            datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            for (int i = 0; i < 128; i++) {
                datagramSocket.send(datagramPacket);
                System.out.println("Sending 128 packets: " + (1 + i) + "\\128");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        float count = 0;
        try {
            System.out.println("Waiting for response...");
            datagramPacket = new DatagramPacket(data, 1, InetAddress.getByName(ip), port);
            datagramSocket.receive(datagramPacket);
            count = data[0];
            count++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        System.out.println("Transfer speed is " + ((count)/((float)(endTime - startTime)/1000000000)) + "kB/s");
        System.out.println("Packets received: " + (100*count/128) + "%");
    }

    private class Resender extends Thread {
        private boolean isStoped = false;

        public void setStop() {
            isStoped = true;
        }

        @Override
        public void run() {
            try {
                while (!isStoped) {
                    datagramPacket = new DatagramPacket(buffer, buffer.length);
                    datagramSocket.receive(datagramPacket);
                    System.out.println("Server: " + new String(buffer, 0, datagramPacket.getLength()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
