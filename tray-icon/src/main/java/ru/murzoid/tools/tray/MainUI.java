package ru.murzoid.tools.tray;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JDialog;


public class MainUI extends JDialog
{
	private SystemTray systemTray = SystemTray.getSystemTray();

	public MainUI() throws IOException, AWTException
	{
		getContentPane().add(new JColorChooser());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		final TrayIcon trayIcon = new TrayIcon(createImage("/favicon.png", "tray icon"));
		trayIcon.setImageAutoSize(true);
	    systemTray = SystemTray.getSystemTray();
		trayIcon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!isVisible()) setVisible(true);
			}
		});
		PopupMenu popupMenu = new PopupMenu();
		MenuItem item = new MenuItem("Exit");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				System.exit(0);
			}
		});
		popupMenu.add(item);
		trayIcon.setPopupMenu(popupMenu);
		systemTray.add(trayIcon);
	}
	
    protected static Image createImage(String path, String description) {
        URL imageURL = MainUI.class.getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
	public static void main(String[] args) throws IOException, AWTException
	{
		MainUI trayWindow = new MainUI();
		trayWindow.pack();
		trayWindow.setVisible(true);
	}
}
