package GomokuClient;

import javax.swing.*;

import com.sun.awt.AWTUtilities;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class MessageBox{

    /**
     * swing������ʾ,
     *
     * @param fontSize
     * @param msg
     * @return
     */
    public MessageBox(int fontSize, String msg) {

        JFrame frame = new JFrame("");// �½�����
        Window win = new Window(frame);// ����Բ��
        // JFrame.setDefaultLookAndFeelDecorated(true);//����ΪswingĬ�ϴ���
        AWTUtilities.setWindowShape(win,
                new RoundRectangle2D.Double(0.0D, 0.0D, win.getWidth(), win.getHeight(), 26.0D, 26.0D));
        Color color = new Color(0, 0, 0, 50);// ��ɫ������͸����Ϊ50��color��͸����ȡֵ��Χ0~255��
        frame.setAlwaysOnTop(true);// ���ô����ö�
        frame.setLayout(new GridBagLayout());// �������������
        frame.setUndecorated(true);// �����ޱ߿�
        frame.setBackground(color);// ���ñ���ɫ

        JLabel label = new JLabel(msg);
        label.setForeground(Color.white);
        label.setFont(new Font("����", 0, fontSize));
        frame.setSize(msg.length() * fontSize, fontSize + 50);
        // ����Ϊ�ַ���С*�ַ����������Ϊ�����С+50����
        frame.add(label);// ��ӵ�����

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        try {
            Thread.sleep(1000);
        } catch (Exception ee) {
            System.exit(0);//�˳�����
        }
        frame.setVisible(false);
    }

}
