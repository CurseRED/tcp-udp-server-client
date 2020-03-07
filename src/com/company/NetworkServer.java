package com.company;

import java.io.File;

public interface NetworkServer {
    void start(int port);
    void startChat();
    void stop();
    void sendMessage(String msg);
    void sendFile(File file);
    void getFile(File file);
    void getConnectionSpeed();
}
