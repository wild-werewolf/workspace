package ru.murzoid.controlled.bipedwalking;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ru.murzoid.controlled.bipedwalking.frame.MyFrame;


public class Shagalka
{

	private static MyFrame frame;
	
	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = MyFrame.setInstance(900, 650, 20, 20);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Управляемая автоколбательная двухногая ходьба");
		addFrame1Listener();
	}	
	private static void addFrame1Listener() {

		frame.addWindowListener(new WindowAdapter()
		{
			public void windowOpened(WindowEvent e)
			{
			}

			public void windowClosing(WindowEvent e)
			{

			}

			public void windowClosed(WindowEvent e)
			{

			}

			public void windowIconified(WindowEvent e)
			{}
		});
	}

}


