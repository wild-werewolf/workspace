/*
 * MainMenu.java
 *
 * Created on 16 Март 2005 г., 14:53
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
     * Создает новые экземпляры MainMenu
     * @param messages текстовые строки, которые используются при создании интерфейса
     */
    public MainMenu(ResourceBundle messages) {

        this.messages = messages;
        
        //создаем пункты меню
        languageMenu = new JMenu(messages.getString("mainMenu.selectLanguage"));
        add(languageMenu);
        
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem(messages.getString("mainMenu.russian"));
        //если текущий язык русский, отмечаем данный пункт меню как выбранный
        if(rbMenuItem.getText().equals("Русский"))
            rbMenuItem.setSelected(true);
        //устанавливаем указатель на функцию обработки нажатий на пункты меню
        rbMenuItem.addActionListener(this);
        rbMenuItem.setActionCommand("setRussian");
        group.add(rbMenuItem);
        languageMenu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem(messages.getString("mainMenu.english"));
        //если текущий язык английский, отмечаем данный пункт меню как выбранный
        if(rbMenuItem.getText().equals("English"))
            rbMenuItem.setSelected(true);
        //устанавливаем указатель на функцию обработки нажатий на пункты меню
        rbMenuItem.addActionListener(this);
        rbMenuItem.setActionCommand("setEnglish");
        group.add(rbMenuItem);
        languageMenu.add(rbMenuItem);
        
        helpMenu = new JMenu(messages.getString("mainMenu.help"));
        add(helpMenu);
        
        menuItem = new JMenuItem(messages.getString("mainMenu.about"));
        menuItem.setActionCommand("about");
        //устанавливаем указатель на функцию обработки нажатий на пункты меню
        menuItem.addActionListener(this);
        helpMenu.add(menuItem);
        
        /*Создаем список, который будет содержать указатели на объекты,
          реализующие интерфейс ChangeLanguageListener. Добавление объектов
          в этот список выполняется в функции createAndShowGUI класса Main.*/
        clListeners = new java.util.ArrayList();
    }

    /**
     * Этот метод выполняет обработку нажатий на пункты меню.
     * @param actionEvent объект, содержащий произошедшее событие
     */
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem src = (JMenuItem)actionEvent.getSource();
        String actionCommand = src.getActionCommand();
        //обработка нажатия на пункт "О программе"
        if(actionCommand.equals("about")) {
            //создаем и показываем диалог, содержащий информацию о программе, авторе,
            //логотип
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
        //обработка нажатия на пункт "Выбор языка -> Английский"
        if(actionCommand.equals("setEnglish")) {
            //устанавливаем английскую локаль, и получает список строк для неё
            Locale eng = new Locale("en");
            messages = ResourceBundle.getBundle("GUIClasses/messages", eng);
            //меняем надписи на компонентах
            setNewLanguage(messages);
        }
        
        //обработка нажатия на пункт "Выбор языка -> Русский"
        if(actionCommand.equals("setRussian")) {
            //устанавливаем русскую локаль, и получает список строк для неё
            Locale ru = new Locale("ru");
            messages = ResourceBundle.getBundle("GUIClasses/messages", ru);
            //меняем надписи на компонентах
            setNewLanguage(messages);
        }
    }

    /**
     * Эта функция изменяет язык надписей всех пунктов меню. Вызывается
     * из функции setNewLanguage.
     * @param mes текстовые строки на заданном языке, которые используются при создании интерфейса
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
    
    /*Эта функция вызывает функции changeLanguage всех объектов,
      которые находятся в списке clListeners. Добавление в этот список
      осуществляется с помощью функции addChangeLanguageListener*/
    private void setNewLanguage(ResourceBundle messages) {
        java.util.Iterator iter = clListeners.iterator();
        while(iter.hasNext()) {
            ((ChangeLanguageListener)iter.next()).changeLanguage(messages);
        }
    }
    
    /**
     * Эта функция регистрирует объекты, для которых пользователь может
     * изменять язык графического интерфейса.
     * @param listener объект, реализующий интерфейс <CODE>ChangeLanguageListener</CODE>
     */
    public void addChangeLanguageListener(ChangeLanguageListener listener) {
        clListeners.add(listener);
    }
}
