package com.company;

import java.io.File;
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
                System.out.print("What do you want to do? (0 - chat, 1 - file transmission, other - measure speed): ");
                answer = in.nextInt();
                if (answer == 0) {
                    tcpServer.startChat();
                    String msg;
                    boolean flag = true;
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
                } else if (answer == 1) {
                    System.out.print("Do you want to send or receive file? (0 - send, other - receive): ");
                    answer = in.nextInt();
                    if (answer == 0) {
                        tcpServer.sendFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\serverFile.txt"));
                    } else {
                        tcpServer.getFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\serverReceivedFile.txt"));
                    }
                } else {
                    tcpServer.getConnectionSpeed();
                }
            } else {
                TcpClient tcpClient = new TcpClient();
                tcpClient.connect("localhost", 7777);
                System.out.print("What do you want to do? (0 - chat, 1 - file transmission, other - measure speed): ");
                answer = in.nextInt();
                if (answer == 0) {
                    tcpClient.startChat();
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
                } else if (answer == 1) {
                    System.out.print("Do you want to send or receive file? (0 - send, other - receive): ");
                    answer = in.nextInt();
                    if (answer == 0) {
                        tcpClient.sendFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\clientFile.txt"));
                    } else {
                        tcpClient.getFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\clientReceivedFile.txt"));
                    }
                } else {
                    tcpClient.getConnectionSpeed();
                }
            }
        } else {
            System.out.print("Start server or client? (0 - server, other - client): ");
            answer = in.nextInt();
            if (answer == 0) {
                UdpServer udpServer = new UdpServer();
                udpServer.start(7777);
                System.out.print("What do you want to do? (0 - chat, 1 - file transmission, other - measure speed): ");
                answer = in.nextInt();
                if (answer == 0) {
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
                } else if (answer == 1) {
                    System.out.print("Do you want to send or receive file? (0 - send, other - receive): ");
                    answer = in.nextInt();
                    if (answer == 0) {
                        udpServer.sendFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\serverFile.txt"));
                    } else {
                        udpServer.getFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\serverReceivedFile.txt"));
                    }
                } else {

                }
            } else {
                UdpClient udpClient = new UdpClient();
                udpClient.connect("localhost", 7777);
                System.out.print("What do you want to do? (0 - chat, 1 - file transmission, other - measure speed): ");
                answer = in.nextInt();
                if (answer == 0) {
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
                } else if (answer == 1) {
                    System.out.print("Do you want to send or receive file? (0 - send, other - receive): ");
                    answer = in.nextInt();
                    if (answer == 0) {
                        udpClient.sendFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\clientFile.txt"));
                    } else {
                        udpClient.getFile(new File("C:\\Users\\Admin\\IdeaProjects\\tcp-udp-client-server\\src\\clientReceivedFile.txt"));
                    }
                } else {

                }
            }
        }

    }
}
