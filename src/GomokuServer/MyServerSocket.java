package GomokuServer;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class MyServerSocket {
    public static void main(String[] args) {
        new ServerListener().start();
    }
}
