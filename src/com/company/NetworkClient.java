package com.company;

public interface NetworkClient {
    void connect(String ip, int port);
    void disconnect();
    void sendMessage(String msg);
    void sendFile();
    void getConnectionSpeed();
}
