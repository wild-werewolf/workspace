package ru.murzoid.controlled.bipedwalking.process;


import ru.murzoid.controlled.bipedwalking.body.ConfigDao;
import ru.murzoid.controlled.bipedwalking.panel.MashinePanel;

public class Going implements Runnable
{	
	public Thread th;
	private MashinePanel mp;
	private ConfigDao config;
	private boolean flag=false;


	public Going(MashinePanel mp)
	{
		th = new Thread(this, "Going");
		this.mp=mp;
	}

	public void start()
	{
		th.start();
		flag=true;
	}

	public void run()
	{
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
		config=ConfigDao.getInstance();
		int countStep=100;
		double stepSize=config.getTwoAlfa()/countStep;
		while(flag) {
			double rez=formulaKvadratUglovoySkorosti();
			if(rez<0){
				continue;
			}
			double mnoj=Math.pow(Math.abs(rez),0.5);
			if(mnoj==Double.NEGATIVE_INFINITY || mnoj==Double.POSITIVE_INFINITY || Double.isNaN(mnoj)) {
				mnoj=1;
			}
			if(config.getTwoAlfaCur()>=config.getTwoAlfa()) {
				config.changeFulcrum();
			} else {
				config.setTwoAlfaCur(config.getTwoAlfaCur()+stepSize);
			}
			mp.reinit(true);
			mp.updateUI();
			try
			{
				Thread.sleep((long) ((long)2/mnoj));
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private double formulaKvadratUglovoySkorosti() {
		if(config.getBeta()>-0.001 && config.getBeta()<0.001) {
			config.setBeta(0.5);
		}
		double step1=config.getSpeed()*Math.pow(formulaOmega(), 2);
		double step2=1/Math.tan(Math.toRadians(Math.abs(config.getTwoAlfaCur())));
		double step3=Math.pow(step2,2);
		double step4a=formulaHau()*(Math.abs(config.getTwoAlfaCur())/2.0);
		double step4b=Math.sin(Math.toRadians(config.getBeta()));
		double step4=step4a*step4b;
		double step5a=Math.sin(Math.toRadians(Math.abs(config.getTwoAlfaCur())/2.0));
		double step5b=Math.sin(Math.toRadians(-config.getDelta()));
		double step5=step5a*step5b;
		double step6=step4+step5;
		double rez=step1*step3*step6;
		return rez;
	}

	private double formulaHau() {
		return (double)config.getKorpLenth()/(double)config.getFootLenth();
	}
	
	private double formulaOmega() {
		return getGi()/config.getFootLenth();
	}

	private double getGi() {
		return 9.8;
	}

	public void stop()
	{
		flag=false;		
	}
	
	
}
