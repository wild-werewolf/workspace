package ru.murzoid.controlled.bipedwalking.body;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.net.URL;

import javax.swing.ImageIcon;

public class Korpus
{
	private int lenth=65;
	private static double rad=Math.PI/180;
	private Point2D endPoint;
	
	private Image massa=null;
	
	public Korpus() {
		Point2D oPoint=ConfigDao.getInstance().getoPoint();
		double beta=ConfigDao.getInstance().getBeta();
		double x=oPoint.getX()+lenth*Math.sin(rad*beta);
		double y=oPoint.getY()+(-1)*lenth*Math.cos(rad*beta);
		endPoint=new Point2D.Double(x,y);
		URL url=getClass().getResource("/ru/murzoid/controlled/bipedwalking/images/lenin.gif");
		massa=new ImageIcon(url).getImage();
	}
	
	public void paint(Graphics2D g) {
		int x1=(int)endPoint.getX()-54/2;
		int y1=(int)endPoint.getY()-45/2;
		Line2D line=new Line2D.Double(ConfigDao.getInstance().getoPoint(), endPoint);
		g.draw(line);
		g.drawImage(massa, x1, y1, 45, 54, null);
	}
	
	public Point2D getEndPoint()
	{
		return endPoint;
	}

	public void reinit() {
		double x=ConfigDao.getInstance().getoPoint().getX()+lenth*Math.sin(rad*ConfigDao.getInstance().getBeta());
		double y=ConfigDao.getInstance().getoPoint().getY()+(-1)*lenth*Math.cos(rad*ConfigDao.getInstance().getBeta());
		endPoint=new Point2D.Double(x,y);
	}
	
	public Image getMassa()
	{
		return massa;
	}

	public void setMassa(Image massa)
	{
		this.massa = massa;
	}
}
