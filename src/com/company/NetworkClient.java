package com.company;

public interface NetworkClient {
    void startConnection(String ip, int port);
    void stopConnection();
    void sendMessage(String msg);
    void sendFile();
    void getConnectionSpeed();
}
