package GomokuServer;

import java.io.*;
import java.net.*;

public class ServerListener extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true) {
                Socket socket = serverSocket.accept();
                //建立连接
                System.out.println( "有客户端连接到12345");
                //将socket传递给新线程
                GomokuSocket cs = new GomokuSocket(socket);
                cs.start();
                cs.gaming=false;

                ServerManager.getChatManager().add(cs);
            }
        } catch (IOException e) {

        }
    }
}
