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
                //��������
                System.out.println( "�пͻ������ӵ�12345");
                //��socket���ݸ����߳�
                GomokuSocket cs = new GomokuSocket(socket);
                cs.start();
                cs.gaming=false;

                ServerManager.getChatManager().add(cs);
            }
        } catch (IOException e) {

        }
    }
}
