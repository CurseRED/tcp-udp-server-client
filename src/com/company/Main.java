package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean flag = true;
        int protocol = -1;
        System.out.println("Select protocol:\n0. TCP\n1. UDP\n");
        while (flag) {
            flag = true;
            Scanner in = new Scanner(System.in);
            protocol = in.nextInt();
            if (protocol < 0 || protocol > 1) {

                System.out.println("Wrong input, try again!");
            } else {
                flag = false;
            }
        }
        System.out.println("Select client or server:\n0. Client\n1. Server\n");
        int client = -1;
        flag = true;
        while (flag) {
            flag = true;
            Scanner in = new Scanner(System.in);
            client = in.nextInt();
            if (client < 0 || client > 1) {
                System.out.println("Wrong input, try again!");
            } else {
                flag = false;
            }
        }
        System.out.println("Select what to do:\n0. Chat\n1. Send files\n2. Measure connection speed\n");
        flag = true;
        int choice = -1;
        while (flag) {
            flag = true;
            Scanner in = new Scanner(System.in);
            choice = in.nextInt();
            if (choice < 0 || choice > 2) {
                System.out.println("Wrong input, try again!");
            } else {
                flag = false;
            }
        }
        if (protocol == 0) {
            if (client == 0) {
                TcpClient tcpClient = new TcpClient();
                tcpClient.startConnection("127.0.0.1", 6666);
                System.out.println("Connected to the server!");
                boolean isStoped = false;
                String msg;
                Scanner in = new Scanner(System.in);
                while (!isStoped) {
                    msg = in.next();
                    tcpClient.sendMessage(msg);
                }
            } else {
                TcpServer tcpServer = new TcpServer();
                tcpServer.start(6666);
                boolean isStoped = false;
                String msg;
                Scanner in = new Scanner(System.in);
                while (!isStoped) {
                    msg = in.next();
                    tcpServer.sendMessage(msg);
                }
            }
        } else {
            if (client == 0) {

            } else {

            }
        }

    }
}
