package GomokuClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.LinkedList;

public class Game_OnLine extends JPanel implements MouseListener, ActionListener {
    private LinkedList<Point> points;
    private int[][] chessArray;
    private boolean isStart;
    private boolean isYourTurn;
    private String board;


    private JButton repent;
    private JButton back;
    private JButton surrender;
    public JTextArea chessMessage;
    private JScrollPane jsp;

    public static Game_OnLine gon = new Game_OnLine();

    public void reset() {
        points.clear();
        board = "board ";
        isStart = false;
        isYourTurn = false;
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                chessArray[i][j] = 0;
        chessMessage.setText("");
    }

    public void setStart(boolean s) {
        isStart = s;
        if (!s) {
            repent.setEnabled(false);
            surrender.setEnabled(false);
        } else {
            repent.setEnabled(true);
            surrender.setEnabled(true);
        }
    }

    public void setMyChess(String chess) {
        if (chess.equals("black")) {
            chessMessage.append("ƥ��ɹ�����Ϸ��ʼ" + "\n");
            chessMessage.append("���ǣ���" + "\n"+"ƥ��ɹ�����Ϸ��ʼ" + "\n");
            isStart = true;
            isYourTurn = true;
        } else if (chess.equals("white")) {
            chessMessage.append("ƥ��ɹ�����Ϸ��ʼ" + "\n");
            chessMessage.append("���ǣ���" + "\n");
            isStart = true;
            isYourTurn = false;
        }
    }

    public void setBoard(String board) throws IOException {

        char c1, c2;
        c1 = board.charAt(board.length() - 2);
        c2 = board.charAt(board.length() - 1);

        Integer x, y;
        x = (Integer.valueOf(c1 - 'a') + 1) * 40;
        y = (Integer.valueOf(c2 - 'a') + 1) * 40;

        setDown(x, y);

    }

    public Game_OnLine() {


        board = "board ";
        isStart = false;
        isYourTurn = false;
        setLayout(null);
        this.addMouseListener(this);

        repent = new JButton("����");
        back = new JButton("����");
        surrender = new JButton("����");

        chessMessage = new JTextArea();
        chessMessage.setTabSize(4);
        chessMessage.setFont(new Font("����", Font.BOLD, 12));
        chessMessage.setLineWrap(true);// �����Զ����й���
        chessMessage.setWrapStyleWord(true);// ������в����ֹ���
        chessMessage.setBackground(Color.white);
        jsp = new JScrollPane(chessMessage);

        chessMessage.setText("���ѽ�����Ϸ����" + "\n" + "���ڵȴ�����" + "\n");


        surrender.setBounds(650, 150, 100, 30);
        surrender.setFocusPainted(false);
        surrender.addActionListener(this);
        surrender.setEnabled(false);


        repent.setBounds(650, 200, 100, 30);
        repent.setFocusPainted(false);
        repent.addActionListener(this);
        repent.setEnabled(false);

        back.setBounds(650, 500, 100, 30);
        back.setFocusPainted(false);
        back.addActionListener(this);
        back.setEnabled(true);

        jsp.setBounds(650, 260, 100, 200);

        add(surrender);
        add(repent);
        add(jsp);
        add(back);
        setSize(800, 700);

        chessArray = new int[15][15];
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                chessArray[i][j] = 0;
        points = new LinkedList<>();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("C:\\Users\\Yuan\\IdeaProjects\\test\\src\\GomokuClient\\img\\2.jpg");
        g.drawImage(img.getImage(), 0, 0, null);

        g.setColor(new Color(205, 190, 112));
        g.fill3DRect(20, 20, 600, 600, true);
        g.setColor(Color.black);
        for (int i = 1; i < 16; i++) {
            g.drawLine(40, 40 * i, 600, 40 * i);
            g.drawLine(40 * i, 40, 40 * i, 600);
        }
        g.fillOval(320 - 5, 320 - 5, 10, 10);
        g.fillOval(160 - 5, 160 - 5, 10, 10);
        g.fillOval(480 - 5, 480 - 5, 10, 10);
        g.fillOval(160 - 5, 480 - 5, 10, 10);
        g.fillOval(480 - 5, 160 - 5, 10, 10);

        for (int i = 0; i < points.size(); i++) {
            if (i % 2 == 0) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.white);
            }
            g.fillOval(points.get(i).x - 15, points.get(i).y - 15, 30, 30);
            if (i == points.size() - 1) {
                g.setColor(Color.red);
                g.fillOval(points.get(i).x - 5, points.get(i).y - 5, 10, 10);
            }
        }

        FiveChessAI s = new FiveChessAI();
        if (s.win(chessArray)) {
            isStart = false;
            if ((points.size() % 2 + 1) == 1) {
                new MessageBox(36, "�����ʤ��");
            } else {
                new MessageBox(36, "�����ʤ��");
            }
            if (isYourTurn) {
                ClientManager.getClientManager().send("over " + board + "\n");
            }
            chessMessage.append("��Ϸ������" + "\r\n");
            setStart(false);
        }

    }

    private void setDown(int x, int y) throws IOException {
        if (!isStart) {
            return;
        }
        int x0 = (x / 40) - 1, y0 = (y / 40) - 1;
        if (chessArray[x0][y0] != 0) {
            return;
        }
        points.add(new Point(x, y));
        chessArray[x / 40 - 1][y / 40 - 1] = points.size() % 2 + 1;
        repaint();
        String str;
        if (points.size() % 2 == 0) {
            str = "��-->";
        } else {
            str = "��-->";
        }
        chessMessage.append(str + (x / 40 - 1) + " " + (y / 40 - 1) + "\r\n");
        board += (char) ((points.get(points.size() - 1).x / 40 - 1) + "a".hashCode());
        board += (char) ((points.get(points.size() - 1).y / 40 - 1) + "a".hashCode());


        if (isYourTurn) {
            ClientManager.getClientManager().send(board);
        }
        isYourTurn = !isYourTurn;
    }


    public void askRepent(boolean isYou) {
        if (isYou) {
            if (isYourTurn) {
                chessMessage.append("����" + (points.get(points.size() - 1).x / 40 - 1) + " " + (points.get(points.size() - 1).y / 40 - 1) + "\r\n");
                chessArray[points.get(points.size() - 1).x / 40 - 1][points.get(points.size() - 1).y / 40 - 1] = 0;
                points.remove(points.size() - 1);
                board = board.substring(0, board.length() - 2);
            }
            chessMessage.append("����" + (points.get(points.size() - 1).x / 40 - 1) + " " + (points.get(points.size() - 1).y / 40 - 1) + "\r\n");
            chessArray[points.get(points.size() - 1).x / 40 - 1][points.get(points.size() - 1).y / 40 - 1] = 0;
            points.remove(points.size() - 1);
            board = board.substring(0, board.length() - 2);
            repaint();
            if (!isYourTurn) {
                isYourTurn = !isYourTurn;
            }

        } else if (!isYou) {
            if (!isYourTurn) {
                chessMessage.append("����" + (points.get(points.size() - 1).x / 40 - 1) + " " + (points.get(points.size() - 1).y / 40 - 1) + "\r\n");
                chessArray[points.get(points.size() - 1).x / 40 - 1][points.get(points.size() - 1).y / 40 - 1] = 0;
                points.remove(points.size() - 1);
                board = board.substring(0, board.length() - 2);
            }
            chessMessage.append("����" + (points.get(points.size() - 1).x / 40 - 1) + " " + (points.get(points.size() - 1).y / 40 - 1) + "\r\n");
            chessArray[points.get(points.size() - 1).x / 40 - 1][points.get(points.size() - 1).y / 40 - 1] = 0;
            points.remove(points.size() - 1);
            board = board.substring(0, board.length() - 2);
            repaint();
            if (isYourTurn) {
                isYourTurn = !isYourTurn;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == surrender) {
            ClientManager.getClientManager().send("surrender " + board + "\n");
        }
        if (e.getSource() == repent) {
            if (points.size() <= 1) {
                new MessageBox(36, "��ǰ���ɻ���");
                return;
            }
            ClientManager.getClientManager().send("repent ask" + "\n");
        }
        if (e.getSource() == back) {

            MainClass.mc.remove(this);
            MainClass.mc.setContentPane(Game_MainMenu.gmm);
            MainClass.mc.setSize(500, 530);
            MainClass.mc.set_Loaction();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getX() < 30 || e.getX() > 610 || e.getY() < 30 || e.getY() > 610) {
            return;
        }

        if (!isStart) {
            new MessageBox(36, "��Ϸ��δ��ʼ");
            return;
        }
        if (!isYourTurn) {
            new MessageBox(36, "��ǰ������Ļغ�");
            return;
        }

        if (chessArray[e.getX() / 40 - 1][e.getY() / 40 - 1] != 0) {
            //new MessageBox(36, "�����ڴ�����");
            return;
        }

        int x1, y1;
        x1 = e.getX();
        y1 = e.getY();
        if (x1 % 40 > 20) {
            x1 += 40;
        }

        if (y1 % 40 > 20) {
            y1 += 40;
        }

        x1 = x1 / 40 * 40;
        y1 = y1 / 40 * 40;

        try {
            setDown(x1, y1);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
