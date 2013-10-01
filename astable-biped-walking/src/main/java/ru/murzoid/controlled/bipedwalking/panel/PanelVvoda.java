package ru.murzoid.controlled.bipedwalking.panel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.AbstractAction;
import javax.swing.Action;

import ru.murzoid.controlled.bipedwalking.body.ConfigDao;
import ru.murzoid.controlled.bipedwalking.frame.MyFrame;

public class PanelVvoda extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PanelVvoda curpan=null; 
	
	private final JPanel pan1;
	private final JPanel pan2;
	private final JPanel pan3;
	private final JPanel pan4;
	private final JPanel pan5;
	BorderLayout lay1;
	BorderLayout lay2;
	BorderLayout lay3;
	BorderLayout lay4;
	BorderLayout lay5;
	private final JLabel l1;
	private final JLabel l2;
	private final JLabel l3;
	private JSpinner sp1;
	private JSpinner sp2;
	private JSpinner sp3;
	private JButton btnStart;
	private JButton btnStop;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();


	static public PanelVvoda getInstance() {
		if(curpan==null) {
			curpan=new PanelVvoda();
		}
		return curpan;
	}
	
	private PanelVvoda()
	{

		lay1 = new BorderLayout();
		lay2 = new BorderLayout();
		lay3 = new BorderLayout();
		lay4 = new BorderLayout();
		lay5 = new BorderLayout();

		pan1 = new JPanel();
		pan2 = new JPanel();
		pan3 = new JPanel();
		pan4 = new JPanel();
		pan5 = new JPanel();
		pan1.setLayout(lay1);
		pan2.setLayout(lay2);
		pan3.setLayout(lay3);
		pan4.setLayout(lay4);
		pan5.setLayout(lay5);

		sp1 = new JSpinner(new SpinnerNumberModel(50.0, 5.0, 85.0, 1.0));
		l1 = new JLabel("Угол между ногой и нормалью к поверхности");

		sp2 = new JSpinner(new SpinnerNumberModel(15.0, 0.0, 90.0, 1.0));
		l2 = new JLabel("Угол наклона спины");
		
		sp3 = new JSpinner(new SpinnerNumberModel(5.0, 0.1, 20.0, .1));
		l3 = new JLabel("Скорость, условные еденицы");

		pan1.add(l1, BorderLayout.NORTH);
		pan1.add(sp1, BorderLayout.CENTER);

		pan2.add(l2, BorderLayout.NORTH);
		pan2.add(sp2, BorderLayout.CENTER);

		pan3.add(l3, BorderLayout.NORTH);
		pan3.add(sp3, BorderLayout.CENTER);
		
		btnStart = new JButton("start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStart.setAction(action);
		pan4.add(btnStart, BorderLayout.CENTER);
		
		btnStop = new JButton("stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStop.setAction(action_1);
		pan5.add(btnStop, BorderLayout.CENTER);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(pan1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(pan2, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(pan3, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(pan4, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(pan5, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(pan1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(pan2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(pan3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(pan4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(pan5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
		);
		setLayout(groupLayout);

	}


	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Старт");
			putValue(SHORT_DESCRIPTION, "Запускает шагалку");
		}
		public void actionPerformed(ActionEvent e) {
			MyFrame.getInstance().stop();
			MyFrame.getInstance().start();
			ConfigDao.getInstance().setTwoAlfa((Double)sp1.getValue());
			ConfigDao.getInstance().setBeta( (Double)sp2.getValue());
			ConfigDao.getInstance().setSpeed((Double)sp3.getValue());
			ConfigDao.getInstance().restartoPoint();
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Стоп");
			putValue(SHORT_DESCRIPTION, "Останавливает шагалку");
		}
		public void actionPerformed(ActionEvent e) {
			MyFrame.getInstance().stop();
		}
	}
}
