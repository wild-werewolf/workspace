package ru.murzoid.controlled.bipedwalking.listener;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import ru.murzoid.controlled.bipedwalking.panel.MashinePanel;

public class MyMove implements MouseMotionListener
{
	private double x_ptr;
	private double y_ptr;
	MashinePanel panel;

	public MyMove(MashinePanel pan)
	{
		panel = pan;
	}
	
	public void mouseMoved(MouseEvent e)
	{
		x_ptr = e.getPoint().getX()/1;
		y_ptr = (e.getPoint().getY())/1;
		
		if (false)
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else 
			panel.setCursor(Cursor.getDefaultCursor());
	}
	public void mouseDragged(MouseEvent ev)
	{

	}
}