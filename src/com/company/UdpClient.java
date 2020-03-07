package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

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
            System.out.println("Client created! Type something to connect the server!");
            resender = new Resender();
            resender.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
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
    public void sendFile() {

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
                    System.out.println("Server: " + new String(buffer, 0, datagramPacket.getLength()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
