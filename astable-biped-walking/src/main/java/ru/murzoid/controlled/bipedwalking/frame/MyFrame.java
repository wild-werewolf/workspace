package ru.murzoid.controlled.bipedwalking.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.murzoid.controlled.bipedwalking.body.ConfigDao;
import ru.murzoid.controlled.bipedwalking.body.EnumFoot;
import ru.murzoid.controlled.bipedwalking.listener.MyM;
import ru.murzoid.controlled.bipedwalking.listener.MyMove;
import ru.murzoid.controlled.bipedwalking.panel.MashinePanel;
import ru.murzoid.controlled.bipedwalking.panel.PanelVvoda;
import ru.murzoid.controlled.bipedwalking.process.Going;

public class MyFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	private static MyFrame curpan;
	
	BorderLayout lay;
	JLabel l1;
	
	final Dimension screenSize;
	final Toolkit kit;

	public MyM mm;
	public MyMove mmv;
	protected String s1;
	protected String s2;
	protected String s3;
	protected String s4;
	protected String s5;
	private MashinePanel panel;
	private int lx;
	private int ly;
	private int height;
	private JPanel labPanel;
	private JLayeredPane pane;
	private JSlider sliderRight;
	private JSlider sliderLeft;

	private PanelVvoda vvod;

	private Going go;


	static public MyFrame getInstance() {
		if(curpan==null) {
			curpan=new MyFrame(900, 650, 20, 20);
		}
		return curpan;
	}
	
	static public MyFrame setInstance(final int x, final int y, final int xN, final int yN) {
		curpan=new MyFrame(x, y, xN, yN);
		return curpan;
	}


	private MyFrame(final int x, final int y, final int xN, final int yN)
	{
		super("Shagalka");
	    pane=getLayeredPane();
		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();
		height=y-40;
		lx = screenSize.width;
		ly = screenSize.height;
		setBounds(xN * lx / 100, yN * ly / 100, x, y);

		ConfigDao.getInstance().setRight(new Point2D.Double(900,height/2));
		ConfigDao.getInstance().setLeft(new Point2D.Double(0,height/2));
		ConfigDao.getInstance().setDelta();
		ConfigDao.getInstance().initFulcrum();
		ConfigDao.getInstance().setFootFulcrum(EnumFoot.LEFT);
		ConfigDao.getInstance().initFi();
		ConfigDao.getInstance().initoPoint();
		
		frameLab();

		setContentPane(labPanel);
		setVisible(true);
		pane.setLayer(labPanel, JLayeredPane.DEFAULT_LAYER);
		
		sliderLeft = new JSlider();
		sliderLeft.setOrientation(SwingConstants.VERTICAL);
		sliderLeft.setMaximum(height);
		sliderLeft.setMinimum(0);
		sliderLeft.setValue((int) height/2);
		sliderLeft.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ConfigDao.getInstance().setLeft(new Point2D.Double(ConfigDao.getInstance().getLeft().getX(),height-sliderLeft.getValue()));
				panel.reinit(true);
				panel.repaint();
			}
		});
		labPanel.add(sliderLeft, BorderLayout.WEST);
		
		sliderRight = new JSlider();
		sliderRight.setOrientation(SwingConstants.VERTICAL);
		sliderRight.setMaximum(height);
		sliderRight.setMinimum(0);
		sliderRight.setValue((int) height/2);
		sliderRight.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ConfigDao.getInstance().setRight(new Point2D.Double(ConfigDao.getInstance().getRight().getX(),height-sliderRight.getValue()));
				panel.reinit(true);
				panel.repaint();
			}
		});
		labPanel.add(sliderRight, BorderLayout.EAST);
		
		revalidate();
		go=new Going(panel);
	}
	
	private void frameLab() {
		this.setTitle("Shagalka");
		BorderLayout lay = new BorderLayout();
		labPanel=new JPanel(true);
		labPanel.setLayout(lay);
		setVisible(true);
		vvod=PanelVvoda.getInstance();
		panel = MashinePanel.getInstance();

		labPanel.add(panel, BorderLayout.CENTER);
		labPanel.add(vvod, BorderLayout.SOUTH);
	}

	
	public void start() {
		go=new Going(panel);
		go.start();
	}

	
	public void revalidate() {
		panel.revalidate();
		super.revalidate();
	}

	public void stop()
	{
		go.stop();
	}

}