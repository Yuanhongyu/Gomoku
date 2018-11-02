package GomokuClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Game_ManMachine extends JPanel implements MouseListener, ActionListener {
    private LinkedList<Point> points;
    private int[][] chessArray;
    private boolean isStart;
    private boolean isYourTurn;
    private String board;

    private JButton start;
    private JButton end;
    private JComboBox whoFirst;
    private JButton repent;
    private JButton back;
    private JTextArea chessMessage;
    private JScrollPane jsp;

    public static Game_ManMachine gmmb = new Game_ManMachine();

    private Game_ManMachine() {
        board = "board:";
        isStart = false;
        isYourTurn = false;
        setLayout(null);
        this.addMouseListener(this);
        String[] s = new String[]{"玩家先行", "AI先行"};
        start = new JButton("开始游戏");
        end = new JButton("结束游戏");
        repent = new JButton("悔棋");
        back = new JButton("返回");
        whoFirst = new JComboBox(s);

        chessMessage = new JTextArea();
        chessMessage.setTabSize(4);
        chessMessage.setFont(new Font("宋体", Font.BOLD, 12));
        chessMessage.setLineWrap(true);// 激活自动换行功能
        chessMessage.setWrapStyleWord(true);// 激活断行不断字功能
        chessMessage.setBackground(Color.white);
        jsp = new JScrollPane(chessMessage);


        whoFirst.setBounds(650, 50, 100, 30);
        whoFirst.setEnabled(true);

        start.setBounds(650, 100, 100, 30);
        start.setFocusPainted(false);
        start.addActionListener(this);
        start.setEnabled(true);

        end.setBounds(650, 150, 100, 30);
        end.setFocusPainted(false);
        end.addActionListener(this);
        end.setEnabled(false);

        repent.setBounds(650, 200, 100, 30);
        repent.setFocusPainted(false);
        repent.addActionListener(this);
        repent.setEnabled(false);

        back.setBounds(650, 500, 100, 30);
        back.setFocusPainted(false);
        back.addActionListener(this);
        back.setEnabled(true);

        jsp.setBounds(650, 260, 100, 200);


        add(whoFirst);
        add(start);
        add(end);
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
            if(i == points.size()-1){
                g.setColor(Color.red);
                g.fillOval(points.get(i).x - 5, points.get(i).y - 5, 10, 10);
            }
        }
    }

    private void writeRecorde(String str) throws IOException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        String time = dateFormat.format(now);

        File f = new File("record.txt");
        FileWriter fw = new FileWriter("record.txt", true);
        fw.write(time + "\r\n");
        fw.write(str + "\r\n");
        fw.close();
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
            str = "○-->";
        } else {
            str = "●-->";
        }
        chessMessage.append(str + (x / 40 - 1) + " " + (y / 40 - 1) + "\r\n");

        FiveChessAI s = new FiveChessAI();
        if (s.win(chessArray)) {
            isStart = false;
            if (isYourTurn) {
                str = "你赢了！";
            } else {
                str = "你输了!";
            }
            chessMessage.append("游戏结束！" + "\r\n");
            new MessageBox(36, str);
            whoFirst.setEnabled(true);
            start.setEnabled(true);
            repent.setEnabled(false);
            end.setEnabled(false);
            for (int i = 0; i < points.size(); i++) {
                board += (char) ((points.get(i).x / 40 - 1) + "a".hashCode());
                board += (char) ((points.get(i).y / 40 - 1) + "a".hashCode());
            }
            writeRecorde(board);
        }

        isYourTurn = !isYourTurn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            board = "board:";
            chessMessage.setText("");
            isStart = true;
            whoFirst.setEnabled(false);
            start.setEnabled(false);
            end.setEnabled(true);
            repent.setEnabled(true);
            for (int i = 0; i < 15; i++)
                for (int j = 0; j < 15; j++)
                    chessArray[i][j] = 0;
            points.clear();

            if (whoFirst.getSelectedItem() == "AI先行") {
                isYourTurn = false;
                FiveChessAI s = new FiveChessAI();
                Optional op = s.testAI(chessArray);
                try {
                    setDown((op.getChessX() + 1) * 40, (op.getChessY() + 1) * 40);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (whoFirst.getSelectedItem() == "玩家先行") {
                isYourTurn = true;
            }

            repaint();
        }
        if (e.getSource() == end) {
            whoFirst.setEnabled(true);
            start.setEnabled(true);
            repent.setEnabled(false);
            end.setEnabled(false);
            isStart = false;

            for (int i = 0; i < points.size(); i++) {
                board += (char) ((points.get(i).x / 40 - 1) + "a".hashCode());
                board += (char) ((points.get(i).y / 40 - 1) + "a".hashCode());

            }
            try {
                writeRecorde(board);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == repent) {
            if (points.size() <= 1) {
                new MessageBox(36, "当前不可悔棋");
                return;
            }
            chessMessage.append("你选择悔棋" + "\r\n");
            chessMessage.append("撤销" + (points.get(points.size() - 1).x / 40 - 1) + " " + (points.get(points.size() - 1).y / 40 - 1) + "\r\n");
            chessArray[points.get(points.size() - 1).x / 40 - 1][points.get(points.size() - 1).y / 40 - 1] = 0;
            points.remove(points.size() - 1);

            chessMessage.append("撤销" + (points.get(points.size() - 1).x / 40 - 1) + " " + (points.get(points.size() - 1).y / 40 - 1) + "\r\n");
            chessArray[points.get(points.size() - 1).x / 40 - 1][points.get(points.size() - 1).y / 40 - 1] = 0;
            points.remove(points.size() - 1);

            repaint();

        }
        if (e.getSource() == back) {
            MainClass.mc.remove(gmmb);
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

        if (!isYourTurn) {
            new MessageBox(36, "当前不是你的回合");
            return;
        }

        if (!isStart) {
            new MessageBox(36, "游戏尚未开始");
            return;
        }

        if (chessArray[e.getX() / 40 - 1][e.getY() / 40 - 1] != 0) {
           // new MessageBox(36, "不能在此落子");
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

        FiveChessAI s = new FiveChessAI();
        Optional op = s.testAI(chessArray);
        try {
            setDown((op.getChessX() + 1) * 40, (op.getChessY() + 1) * 40);
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
