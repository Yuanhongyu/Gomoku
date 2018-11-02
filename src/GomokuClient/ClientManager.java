package GomokuClient;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientManager {
    BufferedReader reader;
    PrintWriter writer;
    Game_OnLine gon;

    Socket socket;

    public static final ClientManager instance = new ClientManager();

    private ClientManager() {
    }

    public static ClientManager getClientManager() {
        return instance;
    }

    public void setPanel(Game_OnLine gon) {
        this.gon = gon;
    }

    public void communicate() throws IOException {
        socket = new Socket("127.0.0.1", 12345);
        new Thread(() -> {
            try {
                writer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));

                reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] msg = line.split(" ");
                    if (msg[0].equals("board")) {
                        gon.setBoard(msg[1]);
                    } else if (msg[0].equals("start")) {
                        gon.setStart(true);
                        gon.setMyChess(msg[1]);
                    } else if (msg[0].equals("surrender")) {
                        if (msg[1].equals("you")) {
                            gon.chessMessage.append("你认输了" + "\n");
                        } else {
                            gon.chessMessage.append("对方认输了" + "\n");
                        }
                        gon.setStart(false);
                    } else if (msg[0].equals("repent")) {

                        if (msg[1].equals("ask")) {
                            Object[] options = {"同意", "拒绝"};
                            int response = JOptionPane.showOptionDialog(gon,
                                    " 对方请求悔棋，是否同意？",
                                    "悔棋", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options[0]);
                            if (response == 0) {
                                send("repent agree" + "\n");
                                gon.chessMessage.append("你同意对方悔棋" + "\n");
                                gon.askRepent(false);

                            } else if (response == 1) {
                                send("repent refuse" + "\n");
                                gon.chessMessage.append("你拒绝对方悔棋" + "\n");
                            }
                        } else if (msg[1].equals("agree")) {
                            gon.chessMessage.append("对方同意悔棋" + "\n");
                            gon.askRepent(true);

                        } else if (msg[1].equals("refuse")) {
                            gon.chessMessage.append("对方拒绝悔棋" + "\n");
                        }

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void send(String out) {
        if (writer != null) {
            writer.write(out + "\n");
            writer.flush();
        } else {
            System.out.println("writer已断开");
        }
    }

}
