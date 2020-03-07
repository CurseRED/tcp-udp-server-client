package com.company;

public interface NetworkServer {
    void start(int port);
    void stop();
    void sendMessage(String msg);
    void sendFile();
    void getConnectionSpeed();
}
