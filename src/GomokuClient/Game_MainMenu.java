package GomokuClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Game_MainMenu extends JPanel implements ActionListener {
    private JButton onlineBattle;
    private JButton outlineBattle;
    private JButton manMachineBattle;
    private JButton reproduce;
    public static Game_MainMenu gmm = new Game_MainMenu();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("C:\\Users\\Yuan\\IdeaProjects\\test\\src\\GomokuClient\\img\\1.png");
        g.drawImage(img.getImage(),0,0,null);
    }

    private Game_MainMenu() {
        setLayout(null);
        onlineBattle = new JButton("在线对战");
        outlineBattle = new JButton("离线对战");
        manMachineBattle = new JButton("人机对战");
        reproduce = new JButton("复      盘");

        onlineBattle.setBounds(180, 210, 100, 40);
        onlineBattle.setBorderPainted(false);
        onlineBattle.setFocusPainted(false);
        onlineBattle.addActionListener(this);

        outlineBattle.setBounds(180, 270, 100, 40);
        outlineBattle.setBorderPainted(false);
        outlineBattle.setFocusPainted(false);
        outlineBattle.addActionListener(this);

        manMachineBattle.setBounds(180, 330, 100, 40);
        manMachineBattle.setBorderPainted(false);
        manMachineBattle.setFocusPainted(false);
        manMachineBattle.addActionListener(this);

        reproduce.setBounds(180, 390, 100, 40);
        reproduce.setBorderPainted(false);
        reproduce.setFocusPainted(false);
        reproduce.addActionListener(this);

        add(onlineBattle);
        add(outlineBattle);
        add(manMachineBattle);
        add(reproduce);
        setSize(500, 500);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==onlineBattle){
            try {
                new StartClient(Game_OnLine.gon);
            } catch (IOException e1) {
                new MessageBox(36,"服务器未启动!");
                return;
            }
            MainClass.mc.remove(gmm);
            Game_OnLine.gon.reset();
            MainClass.mc.setContentPane(Game_OnLine.gon);
            MainClass.mc.setSize(800,700);
            MainClass.mc.set_Loaction();
        }
        if(e.getSource()==outlineBattle){
            MainClass.mc.remove(gmm);
            MainClass.mc.setContentPane(Game_OutLine.gout);
            MainClass.mc.setSize(800,700);
            MainClass.mc.set_Loaction();
        }
        if(e.getSource()==manMachineBattle){
            MainClass.mc.remove(gmm);
            MainClass.mc.setContentPane(Game_ManMachine.gmmb);
            MainClass.mc.setSize(800,700);
            MainClass.mc.set_Loaction();
        }
        if(e.getSource()==reproduce){
            MainClass.mc.remove(gmm);
            try {
                MainClass.mc.setContentPane(new Game_Reproduce());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            MainClass.mc.setSize(850,700);
            MainClass.mc.set_Loaction();

        }
    }
}