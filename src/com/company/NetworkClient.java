package com.company;

import java.io.File;

public interface NetworkClient {
    void connect(String ip, int port);
    void startChat();
    void disconnect();
    void sendMessage(String msg);
    void sendFile(File file);
    void getFile(File file);
    void getConnectionSpeed();
}
