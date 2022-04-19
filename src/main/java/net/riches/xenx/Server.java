package net.riches.xenx;

import java.net.Socket;

public class Server implements Runnable {
    
    private Socket connect;
    static final boolean verbose = true;

    public Server(Socket c) {
        connect = c;
    }

    @Override
    public void run() {

    }
}
