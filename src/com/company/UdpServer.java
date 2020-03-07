package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer implements NetworkServer {
    private byte[] buffer;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private Resender resender;
    private InetAddress clientAddress;
    private int clientPort;

    @Override
    public void startChat() {
        resender = new Resender();
        resender.start();
    }

    @Override
    public void start(int port) {
        buffer = new byte[512];
        try {
            datagramSocket = new DatagramSocket(port);
            System.out.println("Server created!");
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);
            clientAddress = datagramPacket.getAddress();
            clientPort = datagramPacket.getPort();
            System.out.println("Client connected! You can type now!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        resender.setStop();
    }

    @Override
    public void sendMessage(String msg) {
        try {
            datagramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, clientAddress, clientPort);
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
            datagramPacket = new DatagramPacket(data, data.length, clientAddress, clientPort);
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
                    System.out.println("Client: " + new String(buffer, 0, datagramPacket.getLength()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
