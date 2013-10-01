package ru.murzoid.controlled.bipedwalking.body;

import java.awt.geom.Point2D;

public class ConfigDao
{
	private static ConfigDao cdao;
	//������� ���� ��� ����� ����
	private double fiLeft;
	//������� ���� ��� ������ ����
	private double fiRight;
	//��������� ���� ��� ������� ����
	private double fi1;
	//�������� ���� ��� ������� ����
	private double fi2;
	private int korpLenth=65;
	private int footLenth=110;
	private double beta=5;
	private double twoAlfa=30;
	private double twoAlfaCur=twoAlfa;
	private double delta=0;
	private double speed=5;
	private Point2D right;
	private Point2D left;
	//����� ���������� ���������� ������� � ������
	private Point2D oPoint;
	//����� �����
	private Point2D fulcrum;
	private double fiFulcrum;
	private EnumFoot footFulcrum;
	// ���� ����
	private HumanBody body;
	//start=true

	public void setDelta()
	{
		delta =Math.toDegrees( Math.atan(Math.abs(left.getY()-right.getY())/900));
		if(left.getY()<right.getY()) {
			delta*=-1;
		}
	}

	public static ConfigDao getInstance() {
		if(cdao==null) {
			cdao=new ConfigDao();
		}
		return cdao;
	}
	
	public void initoPoint()
	{
		double x=fulcrum.getX()+footLenth*Math.sin(Math.toRadians(fiFulcrum));
		double y=fulcrum.getY()-footLenth*Math.cos(Math.toRadians(fiFulcrum));
		this.oPoint = new Point2D.Double(x, y);
	}
	public void restartoPoint()
	{
		twoAlfaCur=-twoAlfa;
		initFulcrum();
		updateFiFoot();
		initoPoint();
	}

	public void initFi() {
		fi1=-twoAlfa/2+delta;
		fi2=twoAlfa/2+delta;
		updateFiFoot();
	}
	
	public void updateFiFoot()
	{
		int left=-1;
		int right=1;
		if(footFulcrum==EnumFoot.RIGHT) {
			right=-1;
			left=1;
		} else {
			left=-1;
			right=1;
		}
		

		fiLeft=left*twoAlfaCur/2+delta;
		fiRight=right*twoAlfaCur/2+delta;
	}

	public void initFulcrum() {
		fiFulcrum=twoAlfaCur/2-delta;
		fulcrum=new Point2D.Double(left.getX(), left.getY());
	}

	public void updateFulcrum()
	{
		if(left!=null && right!=null && fulcrum!=null) {
			fiFulcrum=twoAlfaCur/2-delta;
			double y=((fulcrum.getX()-left.getX())*(right.getY()-left.getY()))/(right.getX()-left.getX())+left.getY();
			fulcrum.setLocation(fulcrum.getX(), y);
		}
	}
	
	public void updateFiFulcrum()
	{
		fiFulcrum=twoAlfaCur/2-delta;
	}
	
	public void changeFulcrum() {
		if(fulcrum.getX()>right.getX()) {
			restartoPoint();
		} else {
			fulcrum=body.changeFoot(footFulcrum);
			if(footFulcrum==EnumFoot.LEFT) {
				footFulcrum=EnumFoot.RIGHT;
			} else if(footFulcrum==EnumFoot.RIGHT) {
				footFulcrum=EnumFoot.LEFT;
			}
			twoAlfaCur=-twoAlfaCur;
			fiFulcrum=twoAlfaCur/2-delta;
			updateFiFoot();
		}
	}
	
	public Point2D getFulcrum()
	{
		return fulcrum;
	}

	public void setFulcrum(Point2D fulcrum)
	{
		this.fulcrum = fulcrum;
	}

	public double getDelta()
	{
		return delta;
	}

	public double getTwoAlfa()
	{
		return twoAlfa;
	}

	public void setTwoAlfa(double twoAlfa)
	{
		this.twoAlfa = twoAlfa;
	}

	public double getBeta()
	{
		return beta;
	}

	public void setBeta(double beta)
	{
		this.beta = beta;
	}

	public Point2D getRight()
	{
		return right;
	}

	public void setRight(Point2D right)
	{
		this.right = right;
		updateFulcrum();
	}

	public Point2D getLeft()
	{
		return left;
	}

	public void setLeft(Point2D left)
	{
		this.left = left;
		updateFulcrum();
	}

	public Point2D getoPoint()
	{
		return oPoint;
	}

	public void setoPoint(Point2D oPoint)
	{
		this.oPoint = oPoint;
	}

	public int getKorpLenth()
	{
		return korpLenth;
	}

	public void setKorpLenth(int korpLenth)
	{
		this.korpLenth = korpLenth;
	}

	public int getFootLenth()
	{
		return footLenth;
	}

	public void setFootLenth(int footLenth)
	{
		this.footLenth = footLenth;
	}

	
	public double getFiLeft()
	{
		return fiLeft;
	}

	public void setFiLeft(double fiLeft)
	{
		this.fiLeft = fiLeft;
	}

	public double getFiRight()
	{
		return fiRight;
	}

	public void setFiRight(double fiRight)
	{
		this.fiRight = fiRight;
	}

	public double getFi1()
	{
		return fi1;
	}

	public void setFi1(double fi1)
	{
		this.fi1 = fi1;
	}

	public double getFi2()
	{
		return fi2;
	}

	public void setFi2(double fi2)
	{
		this.fi2 = fi2;
	}

	public double getTwoAlfaCur()
	{
		return twoAlfaCur;
	}

	public void setTwoAlfaCur(double twoAlfaCur)
	{
		this.twoAlfaCur = twoAlfaCur;
	}

	public double getFiFulcrum()
	{
		return fiFulcrum;
	}

	public void setFiFulcrum(double fiFulcrum)
	{
		this.fiFulcrum = fiFulcrum;
	}

	public HumanBody getBody()
	{
		return body;
	}

	public void setBody(HumanBody body)
	{
		this.body = body;
	}

	public EnumFoot getFootFulcrum()
	{
		return footFulcrum;
	}

	public void setFootFulcrum(EnumFoot footFulcrum)
	{
		this.footFulcrum = footFulcrum;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
