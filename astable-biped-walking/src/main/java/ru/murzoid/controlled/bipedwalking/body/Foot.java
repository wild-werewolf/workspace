package ru.murzoid.controlled.bipedwalking.body;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Foot
{
	private int lenth=ConfigDao.getInstance().getFootLenth();
	private static double rad=Math.PI/180;
	private Point2D endPoint;
	private EnumFoot footType;
	
	public Foot(double fi, EnumFoot footType) {
		Point2D oPoint=ConfigDao.getInstance().getoPoint();
		double x=oPoint.getX()+lenth*Math.sin(rad*fi);
		double y=oPoint.getY()+lenth*Math.cos(rad*fi);
		endPoint=new Point2D.Double(x,y);
		this.footType=footType;
	}
	
	public void paint(Graphics2D g) {
		Line2D line=new Line2D.Double(ConfigDao.getInstance().getoPoint(), endPoint);
		Color color=g.getColor();
		g.setPaint(footType.getColor());
		g.draw(line);
		g.setColor(color);
	}

	public Point2D getEndPoint()
	{
		return endPoint;
	}

	public void setEndPoint(Point2D endPoint)
	{
		this.endPoint = endPoint;
	}
	
	public void reinit(double fi) {
		Point2D oPoint=ConfigDao.getInstance().getoPoint();
		double x=oPoint.getX()+lenth*Math.sin(rad*fi);
		double y=oPoint.getY()+lenth*Math.cos(rad*fi);
		endPoint=new Point2D.Double(x,y);
	}

	public EnumFoot getFootType()
	{
		return footType;
	}

	public void setFootType(EnumFoot footType)
	{
		this.footType = footType;
	}
}
