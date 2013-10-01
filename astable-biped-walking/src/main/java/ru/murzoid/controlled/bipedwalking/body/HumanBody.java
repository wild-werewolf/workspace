package ru.murzoid.controlled.bipedwalking.body;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class HumanBody
{
	private Korpus korp=new Korpus();
	private Foot footLeft=new Foot(ConfigDao.getInstance().getFiLeft(),EnumFoot.LEFT);
	private Foot footRight=new Foot(ConfigDao.getInstance().getFiRight(),EnumFoot.RIGHT);
	
	public void paint(Graphics2D g) {
		korp.paint(g);
		footLeft.paint(g);
		footRight.paint(g);
	}
	
	public void reinit() {
		korp.reinit();
		footLeft.reinit(ConfigDao.getInstance().getFiLeft());   //сначала опорная 
		footRight.reinit(ConfigDao.getInstance().getFiRight());	//сначала впереди 
	}
	
	public Point2D changeFoot(EnumFoot footCur) {
		if(footCur==EnumFoot.LEFT) {
			return footRight.getEndPoint();
		} else {
			return footLeft.getEndPoint();
		}
	}
}
