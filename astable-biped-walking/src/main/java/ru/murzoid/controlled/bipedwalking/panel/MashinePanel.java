package ru.murzoid.controlled.bipedwalking.panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import ru.murzoid.controlled.bipedwalking.body.ConfigDao;
import ru.murzoid.controlled.bipedwalking.body.HumanBody;
import ru.murzoid.controlled.bipedwalking.listener.MyM;
import ru.murzoid.controlled.bipedwalking.listener.MyMove;


public class MashinePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MashinePanel curpan;
	final int radius = 20;
	final Color col = Color.CYAN;
	// ////////////////////////////////
	private Image dbImage;
	private Graphics dbg;
	// ////////////////////////////////////////
	private final MyM mm;
	private final MyMove mmv;
	private Line2D earth;
	private Line2D earth_p;
	

	static public MashinePanel getInstance() {
		if(curpan==null) {
			curpan=new MashinePanel();
		}
		return curpan;
	}
	
	private MashinePanel() {
		mm = new MyM(this);
		mmv = new MyMove(this);
		addMouseListener(mm);
		addMouseMotionListener(mmv);
		ConfigDao.getInstance().setBody(new HumanBody());
		earth=new Line2D.Double(ConfigDao.getInstance().getLeft(),ConfigDao.getInstance().getRight());
	}
	
	
	public void update(Graphics g)
	{
		if (dbImage == null)
		{
			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}
		// очищаем экран на заднем плане
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
		// рисуем элементы на заднем плане
		dbg.setColor(getForeground());
		paint(dbg);
		// рисуем картинку на экране
		g.drawImage(dbImage, 0, 0, this);

	}

	public void paintComponent(Graphics g)
	{
		final Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, 900, 900);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		ConfigDao.getInstance().getBody().paint(g2);
		earth=new Line2D.Double(ConfigDao.getInstance().getLeft(), ConfigDao.getInstance().getRight());
		g2.draw(earth);
	}

	public Line2D getEarth_p()
	{
		return earth_p;
	}
	
	
	
	public void reinit(boolean all) {
		if(all) {
			ConfigDao.getInstance().setDelta();
			ConfigDao.getInstance().updateFiFulcrum();
			ConfigDao.getInstance().initFi();
			ConfigDao.getInstance().initoPoint();
		}
		ConfigDao.getInstance().getBody().reinit();
	}

}
