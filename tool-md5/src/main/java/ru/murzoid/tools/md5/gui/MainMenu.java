/*
 * MainMenu.java
 *
 * Created on 16 ���� 2005 �., 14:53
 */

package ru.murzoid.tools.md5.gui;

import javax.swing.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import java.util.Locale;
import ru.murzoid.tools.md5.listeners.ChangeLanguageListener;


public class MainMenu extends JMenuBar implements ActionListener,
    ChangeLanguageListener {
    
    private JMenu languageMenu;
    private JMenu helpMenu;
    private JMenuItem menuItem;
    private JRadioButtonMenuItem rbMenuItem;
    private ResourceBundle messages;
    private java.util.List clListeners;
    
    /**
     * ������� ����� ���������� MainMenu
     * @param messages ��������� ������, ������� ������������ ��� �������� ����������
     */
    public MainMenu(ResourceBundle messages) {

        this.messages = messages;
        
        //������� ������ ����
        languageMenu = new JMenu(messages.getString("mainMenu.selectLanguage"));
        add(languageMenu);
        
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem(messages.getString("mainMenu.russian"));
        //���� ������� ���� �������, �������� ������ ����� ���� ��� ���������
        if(rbMenuItem.getText().equals("�������"))
            rbMenuItem.setSelected(true);
        //������������� ��������� �� ������� ��������� ������� �� ������ ����
        rbMenuItem.addActionListener(this);
        rbMenuItem.setActionCommand("setRussian");
        group.add(rbMenuItem);
        languageMenu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem(messages.getString("mainMenu.english"));
        //���� ������� ���� ����������, �������� ������ ����� ���� ��� ���������
        if(rbMenuItem.getText().equals("English"))
            rbMenuItem.setSelected(true);
        //������������� ��������� �� ������� ��������� ������� �� ������ ����
        rbMenuItem.addActionListener(this);
        rbMenuItem.setActionCommand("setEnglish");
        group.add(rbMenuItem);
        languageMenu.add(rbMenuItem);
        
        helpMenu = new JMenu(messages.getString("mainMenu.help"));
        add(helpMenu);
        
        menuItem = new JMenuItem(messages.getString("mainMenu.about"));
        menuItem.setActionCommand("about");
        //������������� ��������� �� ������� ��������� ������� �� ������ ����
        menuItem.addActionListener(this);
        helpMenu.add(menuItem);
        
        /*������� ������, ������� ����� ��������� ��������� �� �������,
          ����������� ��������� ChangeLanguageListener. ���������� ��������
          � ���� ������ ����������� � ������� createAndShowGUI ������ Main.*/
        clListeners = new java.util.ArrayList();
    }

    /**
     * ���� ����� ��������� ��������� ������� �� ������ ����.
     * @param actionEvent ������, ���������� ������������ �������
     */
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem src = (JMenuItem)actionEvent.getSource();
        String actionCommand = src.getActionCommand();
        //��������� ������� �� ����� "� ���������"
        if(actionCommand.equals("about")) {
            //������� � ���������� ������, ���������� ���������� � ���������, ������,
            //�������
            String text = messages.getString("about.aboutInfo");
            String logoFileName = messages.getString("about.logo");
            java.net.URL logoURL = MainMenu.class.getResource("/md5calc/images/"
                    + logoFileName);
            ImageIcon logoIcon = null;
            if(logoURL != null) {
                logoIcon = new ImageIcon(logoURL);
            }
            JOptionPane.showMessageDialog(this, text, messages.getString("mainMenu.about"),
                    JOptionPane.PLAIN_MESSAGE, logoIcon);
        }
        //��������� ������� �� ����� "����� ����� -> ����������"
        if(actionCommand.equals("setEnglish")) {
            //������������� ���������� ������, � �������� ������ ����� ��� ��
            Locale eng = new Locale("en");
            messages = ResourceBundle.getBundle("GUIClasses/messages", eng);
            //������ ������� �� �����������
            setNewLanguage(messages);
        }
        
        //��������� ������� �� ����� "����� ����� -> �������"
        if(actionCommand.equals("setRussian")) {
            //������������� ������� ������, � �������� ������ ����� ��� ��
            Locale ru = new Locale("ru");
            messages = ResourceBundle.getBundle("GUIClasses/messages", ru);
            //������ ������� �� �����������
            setNewLanguage(messages);
        }
    }

    /**
     * ��� ������� �������� ���� �������� ���� ������� ����. ����������
     * �� ������� setNewLanguage.
     * @param mes ��������� ������ �� �������� �����, ������� ������������ ��� �������� ����������
     */
    public void changeLanguage(ResourceBundle mes) {
        this.messages = mes;
        
        languageMenu.setText(mes.getString("mainMenu.selectLanguage"));
        java.awt.Component[] menuElements = languageMenu.getMenuComponents();
        ((AbstractButton)menuElements[0]).setText(mes.getString("mainMenu.russian"));
        ((AbstractButton)menuElements[1]).setText(mes.getString("mainMenu.english"));
        
        helpMenu.setText(mes.getString("mainMenu.help"));
        menuElements = helpMenu.getMenuComponents();
        ((AbstractButton)menuElements[0]).setText(mes.getString("mainMenu.about"));
    }
    
    /*��� ������� �������� ������� changeLanguage ���� ��������,
      ������� ��������� � ������ clListeners. ���������� � ���� ������
      �������������� � ������� ������� addChangeLanguageListener*/
    private void setNewLanguage(ResourceBundle messages) {
        java.util.Iterator iter = clListeners.iterator();
        while(iter.hasNext()) {
            ((ChangeLanguageListener)iter.next()).changeLanguage(messages);
        }
    }
    
    /**
     * ��� ������� ������������ �������, ��� ������� ������������ �����
     * �������� ���� ������������ ����������.
     * @param listener ������, ����������� ��������� <CODE>ChangeLanguageListener</CODE>
     */
    public void addChangeLanguageListener(ChangeLanguageListener listener) {
        clListeners.add(listener);
    }
}
