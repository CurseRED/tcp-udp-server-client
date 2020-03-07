package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Start server or client? (0 - server, other - client): ");
        Scanner in = new Scanner(System.in);
        int answer = in.nextInt();
        if (answer == 0) {
            TcpServer tcpServer = new TcpServer();
            tcpServer.start(7777);
            String msg;
            boolean flag = true;
            in.reset();
            while (flag) {
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
            in.reset();
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
    }
}
