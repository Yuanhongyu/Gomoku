package GomokuClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainClass extends JFrame {
    public static MainClass mc;
    private boolean isFirst;

    public void set_Loaction() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public MainClass(boolean isFirst) throws IOException {
        this.isFirst = isFirst;
        mc = this;
        setLayout(null);
        setTitle("Îå×ÓÆå");

        add(Game_MainMenu.gmm);
        setSize(500, 530);
        set_Loaction();
        setAlwaysOnTop(true);
        setResizable(false);
        //setAlwaysOnTop(true);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        MainClass mm = new MainClass(false);
        mm.setVisible(true);
    }

}

