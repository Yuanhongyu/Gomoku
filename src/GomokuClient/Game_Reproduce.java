package GomokuClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Game_Reproduce extends JPanel implements ActionListener {
    private LinkedList<String> times;
    private LinkedList<String> boards;
    private LinkedList<Point> points;

    int nowChess;
    private String nowBoard;
    private boolean isStart;
    public static Game_Reproduce gr;

    static {
        try {
            gr = new Game_Reproduce();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton start;
    private JButton nextStep;
    private JButton preStep;
    private JButton back;
    private JComboBox whichRecord;

    public Game_Reproduce() throws IOException {
        setLayout(null);

        int count = 0;
        nowChess = 0;
        isStart = false;
        points = new LinkedList<>();
        times = new LinkedList<>();
        boards = new LinkedList<>();

        start = new JButton("复      盘");
        preStep = new JButton("上一步");
        nextStep = new JButton("下一步");
        back = new JButton("返       回");

        FileReader fr = new FileReader("record.txt");
        BufferedReader br = new BufferedReader(fr);
        String str = br.readLine();
        while (str != null) {
            if (count % 2 == 0) {
                times.add(str);
            } else {
                str = str.substring(6, str.length());
                boards.add(str);
            }
            count++;
            str = br.readLine();
        }
        br.close();
        fr.close();

        whichRecord = new JComboBox(times.toArray(new String[times.size()]));
        whichRecord.setBounds(650, 50, 120, 30);
        whichRecord.setEnabled(true);

        start.setBounds(650, 100, 100, 50);
        start.setFocusPainted(false);
        start.addActionListener(this);
        start.setEnabled(true);

        preStep.setBounds(650, 200, 100, 50);
        preStep.setFocusPainted(false);
        preStep.addActionListener(this);
        preStep.setEnabled(true);

        nextStep.setBounds(650, 300, 100, 50);
        nextStep.setFocusPainted(false);
        nextStep.addActionListener(this);
        nextStep.setEnabled(true);

        back.setBounds(650, 450, 100, 50);
        back.setFocusPainted(false);
        back.addActionListener(this);
        back.setEnabled(true);

        add(whichRecord);
        add(start);
        add(preStep);
        add(nextStep);
        add(back);

        setSize(850, 700);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("C:\\Users\\Yuan\\IdeaProjects\\test\\src\\GomokuClient\\img\\2.jpg");
        g.drawImage(img.getImage(), 0, 0, null);

        g.setColor(new Color(205, 190, 112));
        g.fill3DRect(20, 20, 600, 600, true);
        g.setColor(Color.black);
        g.fillOval(320 - 5, 320 - 5, 10, 10);
        g.fillOval(160 - 5, 160 - 5, 10, 10);
        g.fillOval(480 - 5, 480 - 5, 10, 10);
        g.fillOval(160 - 5, 480 - 5, 10, 10);
        g.fillOval(480 - 5, 160 - 5, 10, 10);
        for (int i = 1; i < 16; i++) {
            g.drawLine(40, 40 * i, 600, 40 * i);
            g.drawLine(40 * i, 40, 40 * i, 600);
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            points.clear();
            nowChess = 0;
            repaint();
            if (times.size() <= 0) {
                new MessageBox(36, "当前没有棋局");
                return;
            }
            nowBoard = boards.get(whichRecord.getSelectedIndex());
            isStart = true;
        }
        if (e.getSource() == preStep) {
            if (!isStart) {
                new MessageBox(36, "尚未开始复盘");
                return;
            }
            if (nowChess <= 0) {
                new MessageBox(36, "无上一步");
                return;
            }
            points.pollLast();
            repaint();
            nowChess--;

        }
        if (e.getSource() == nextStep) {
            if (!isStart) {
                new MessageBox(36, "尚未开始复盘");
                return;
            }
            if (nowChess >= (nowBoard.length() / 2)) {
                new MessageBox(36, "无下一步");
                return;
            }
            char c1 = nowBoard.charAt(nowChess * 2);
            char c2 = nowBoard.charAt(nowChess * 2 + 1);
            Integer x, y;
            x = (Integer.valueOf(c1 - 'a') + 1) * 40;
            y = (Integer.valueOf(c2 - 'a') + 1) * 40;

            points.add(new Point(x, y));
            repaint();
            nowChess++;

        }
        if (e.getSource() == back) {
            MainClass.mc.remove(gr);
            MainClass.mc.setContentPane(Game_MainMenu.gmm);
            MainClass.mc.setSize(500, 530);
            MainClass.mc.set_Loaction();
        }

    }
}

