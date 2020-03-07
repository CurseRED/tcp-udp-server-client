package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("TCP or UDP protocol? (0 - TCP, other - UDP): ");
        Scanner in = new Scanner(System.in);
        int answer = in.nextInt();
        if (answer == 0) {
            System.out.print("Start server or client? (0 - server, other - client): ");
            answer = in.nextInt();
            if (answer == 0) {
                TcpServer tcpServer = new TcpServer();
                tcpServer.start(7777);
                String msg;
                boolean flag = true;
                while (flag) {
                    System.out.println("KKEKEKEK");
                    msg = in.nextLine();
                    if (msg.equalsIgnoreCase("!stop")) {
                        flag = false;
                    } else {
                        if (!msg.isEmpty())
                            tcpServer.sendMessage(msg);
                    }
                }
                tcpServer.stop();
            } else {
                TcpClient tcpClient = new TcpClient();
                tcpClient.connect("localhost", 7777);
                String msg;
                boolean flag = true;
                while (flag) {
                    msg = in.nextLine();
                    if (msg.equalsIgnoreCase("!stop")) {
                        flag = false;
                    } else {
                        if (!msg.isEmpty())
                            tcpClient.sendMessage(msg);
                    }
                }
                tcpClient.disconnect();
            }
        } else {
            System.out.print("Start server or client? (0 - server, other - client): ");
            answer = in.nextInt();
            if (answer == 0) {
                UdpServer udpServer = new UdpServer();
                udpServer.start(7777);
                String msg;
                boolean flag = true;
                while (flag) {
                    msg = in.nextLine();
                    if (msg.equalsIgnoreCase("!stop")) {
                        flag = false;
                    } else {
                        if (!msg.isEmpty())
                            udpServer.sendMessage(msg);
                    }
                }
                udpServer.stop();
            } else {
                UdpClient udpClient = new UdpClient();
                udpClient.connect("localhost", 7777);
                String msg;
                boolean flag = true;
                while (flag) {
                    msg = in.nextLine();
                    if (msg.equalsIgnoreCase("!stop")) {
                        flag = false;
                    } else {
                        if (!msg.isEmpty())
                            udpClient.sendMessage(msg);
                    }
                }
                udpClient.disconnect();
            }
        }

    }
}
