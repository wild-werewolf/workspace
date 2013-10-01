package ru.murzoid.controlled.bipedwalking.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.murzoid.controlled.bipedwalking.panel.MashinePanel;

	public class MyM extends MouseAdapter
	{
		MashinePanel panel;
		
		ExecutorService es = Executors.newFixedThreadPool(1);
		
		public MyM(MashinePanel pan)
		{
			panel = pan;
		}
		
		public void mousePressed(MouseEvent e	)
		{


		}
		public void mouseClicked(MouseEvent ev)
		{
			
		}
	}