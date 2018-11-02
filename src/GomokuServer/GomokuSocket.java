package GomokuServer;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GomokuSocket extends Thread {
    Socket socket;
    public String chess;
    public boolean gaming;

    public GomokuSocket(Socket s) {
        this.socket = s;
    }

    public void out(String out) throws IOException {
        socket.getOutputStream().write(out.getBytes("UTF-8"));
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] msg = line.split(" ");

                if (msg[0].equals("surrender")) {
                    Date now = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
                    String time = dateFormat.format(now);

                    File f = new File("record.txt");
                    FileWriter fw = new FileWriter("record.txt", true);
                    fw.write(time + "\r\n");
                    fw.write(msg[1] + ":" + msg[2] + "\r\n");
                    fw.close();

                    this.gaming = false;
                    this.out("surrender you" + "\n");
                    ServerManager.getChatManager().getOp(this).gaming = false;
                    ServerManager.getChatManager().getOp(this).out("surrender op" + "\n");

                } else if (msg[0].equals("board")) {
                    ServerManager.getChatManager().publish(this, line);
                } else if (msg[0].equals("repent")) {
                    if (msg[1].equals("ask")) {
                        ServerManager.getChatManager().getOp(this).out("repent ask" + "\n");
                    } else if (msg[1].equals("agree")) {
                        ServerManager.getChatManager().getOp(this).out("repent agree" + "\n");
                    } else if (msg[1].equals("refuse")) {
                        ServerManager.getChatManager().getOp(this).out("repent refuse" + "\n");
                    }
                } else if (msg[0].equals("over")) {
                    Date now = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
                    String time = dateFormat.format(now);

                    File f = new File("record.txt");
                    FileWriter fw = new FileWriter("record.txt", true);
                    fw.write(time + "\r\n");
                    fw.write(msg[1] + ":" + msg[2] + "\r\n");
                    fw.close();

                    this.gaming = false;
                    ServerManager.getChatManager().getOp(this).gaming = false;
                }


            }
            br.close();
        } catch (IOException e) {
        }
    }
}
