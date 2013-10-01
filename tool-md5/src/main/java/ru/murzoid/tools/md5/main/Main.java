/*
 * Main.java
 *
 * Created on 13 ���� 2005 �., 12:02
 */

package ru.murzoid.tools.md5.main;

import javax.swing.*;

import ru.murzoid.tools.md5.gui.*;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main {
    
    /* ������� ����� ���������� ������ Main */
    public Main() {
    }
    
    /*��� ������� �������� ������ ����� � ���������*/
    public static void main(String[] args) {
        //������� ��������� ����� ��� ������ � ����������� �����������
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //���������� ������� ������������ ��������� (������������ ���
                //��������� ��������� �����)
                Locale curLocale = Locale.getDefault();
                //��������� ��������� ������ ��� ������� ����� (������� ��
                //�������, ���� � �.�.)
                ResourceBundle messages = ResourceBundle.getBundle("ru.murzoid.tools.md5.gui/messages",
                        curLocale);
                createAndShowGUI(messages);
            }
        });
    }
    
    /*��� ������� ������� ������� ���� ���������*/
    private static void createAndShowGUI(ResourceBundle messages) {
        try {
            //������������� ��������� ������� ��� (���� ��� ��������)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {}
        
        //������� ������� �����
        JFrame frame = new JFrame(messages.getString("title"));
        //��������� ������ ���������� ������ ��� �������� �������� ����
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //��������� ������ � ������������� � ������ "�������� �����"
        java.net.URL imageURL = Main.class.getResource("images/icon24x24.png");
        if(imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            frame.setIconImage(icon.getImage());
        }
        
        //������� ������ ��� ������ � �������������, � ������������� �
        //������� ������� ����
        JComponent contentPane = new FolderTreeAnalyserPanel(messages, frame);
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);
        
        //������� ���� � ��������� ��� � ����
        ru.murzoid.tools.md5.gui.MainMenu mainMenu = new ru.murzoid.tools.md5.gui.MainMenu(messages);
        frame.setJMenuBar(mainMenu);
        
        //������������ �������, ������� ������ �������� ���� ����������
        //���� ������������ ������� ���� ����������
        mainMenu.addChangeLanguageListener(mainMenu);
        mainMenu.addChangeLanguageListener((FolderTreeAnalyserPanel)contentPane);
        
        //����������� ����� � ������ ��� �������
        frame.pack();
        frame.setVisible(true);
    }
}
