/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ru.murzoid.project.client.vacuum;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

/**
 * Simple Demo of gradient fills and strokes.
 */
public class LabVacuumCanvas extends SimpleCanvas {

	class GradientDemoControls extends Composite {
		public GradientDemoControls() {
			VerticalPanel layout = new VerticalPanel();
			layout.add(new HTML(
					"<b style=\"color:#f00;\">Gradients currently not working correctly in IE. Contributor assistance welcome :).</b>"));
			initWidget(layout);
		}
	}

	private List<ImageElement> motorImg=new ArrayList<ImageElement>();
	private List<ImageElement> waterImg=new ArrayList<ImageElement>();
	private ImageElement bakImg;
	private Timer timer;
	private int iter=0;
	private int iterW=0;
	private double y_h1=167;
	private double y_h2=121;
	private double y_h=181;
	
	private double y_vis=121;
	
	private double x_s1=22;
	private double x_s=53;
	private double x_s2=81;
	private boolean motor=true;
	private boolean zapolnenie=false;
	private boolean sliv=false;
	private boolean clean=false;
	private boolean waterEnd=false;
	private boolean waterFull=false;
	private double speedBak=1.0;
	private double osad=0;
	private double step=0.5;
	private double stepV=1.5;
	private double vc=0;
	private int time=40;
	private boolean fullFilt;

	public LabVacuumCanvas(GWTCanvas theCanvas) {
		super(theCanvas);
		demoName = "Вакуум-Фильтр";
	}

	public void createControls() {
		controls = new GradientDemoControls();
	}

	public void drawDemo(int width, int height) {
		canvas.resize(width, height);

		
		canvas.saveContext();

		// ////////////////////////////////////

		timer = new Timer() {

			@Override
			public void run() {
				renderingLoop();
			}

		};
		String[] imageUrls = new String [30];
		imageUrls[0]=new String("../images/imgbak.png");
		imageUrls[1]=new String("../images/img0000.png");
		imageUrls[2]=new String("../images/img0001.png");
		imageUrls[3]=new String("../images/img0002.png");
		imageUrls[4]=new String("../images/img0003.png");
		imageUrls[5]=new String("../images/img0004.png");
		imageUrls[6]=new String("../images/img0005.png");
		imageUrls[7]=new String("../images/img0006.png");
		imageUrls[8]=new String("../images/img0007.png");
		imageUrls[9]=new String("../images/img0008.png");
		imageUrls[10]=new String("../images/img0009.png");
		imageUrls[11]=new String("../images/img0010.png");
		imageUrls[12]=new String("../images/img0011.png");
		imageUrls[13]=new String("../images/img0012.png");
		imageUrls[14]=new String("../images/img0013.png");
		imageUrls[15]=new String("../images/img0014.png");
		imageUrls[16]=new String("../images/img0015.png");
		imageUrls[17]=new String("../images/img0016.png");
		imageUrls[18]=new String("../images/img0017.png");
		imageUrls[19]=new String("../images/img0018.png");
		imageUrls[20]=new String("../images/img0019.png");
		imageUrls[21]=new String("../images/img0020.png");
		imageUrls[22]=new String("../images/img0021.png");
		imageUrls[23]=new String("../images/img0022.png");
		imageUrls[24]=new String("../images/img0023.png");
		imageUrls[25]=new String("../images/img0024.png");
		imageUrls[26]=new String("../images/img0025.png");
		imageUrls[27]=new String("../images/img0026.png");
		imageUrls[28]=new String("../images/img0027.png");
		imageUrls[29]=new String("../images/img0028.png");
		
		if (bakImg==null) {
			ImageLoader.loadImages(imageUrls, new ImageLoader.CallBack() {
				@Override
				public void onImagesLoaded(ImageElement[] imageHandles) {
					bakImg=imageHandles[0];
					for(int i=1;i<imageHandles.length;i++){
						if(i<6){
							motorImg.add(imageHandles[i]);
						} else {
							waterImg.add(imageHandles[i]);
						}
					}
					timer.schedule(40);
				}
			});
		} else {
			if (isImageLoaded(bakImg)) {
				timer.schedule(40);
			} else {
				Window.alert("Refresh the page to reload the image.");
			}
		}
		/////////////////////////////////////
		canvas.restoreContext();
	}

	private native boolean isImageLoaded(ImageElement imgElem) /*-{
		return !!imgElem.__isLoaded;
	}-*/;

	private void renderingLoop() {
		canvas.clear();
		canvas.drawImage(bakImg, 0, 0, 640, 480, 0, 180, 320, 240);
		if(sliv ){
			if(waterFull){
				sliv();
				canvas.drawImage(waterImg.get(iterW), 0, 0, 320, 240, 2, 35, 210, 240);
				iterW++;
				if(iterW>=16){
					iterW=12;
				}
				if(iterW>=12) {
					osad-=step;
					vc-=stepV;
				}
			} else {
				sliv=false;
			}
		} 
		if(waterEnd){
			canvas.drawImage(waterImg.get(iterW), 0, 0, 320, 240, 2, 35, 210, 240);
			iterW++;
			if(iterW>=24){
				iterW=0;
				sliv=false;
				waterEnd=false;
			}
		}
		canvas.drawImage(motorImg.get(iter), 0, 0, 320, 240, 0, 0, 320, 240);
		if(motor){
			iter++;
			if(iter>=5){
				iter=0;
			}
		}
		canvas.setFillStyle(new Color("rgba(0,0,215,0.5)"));
		if(zapolnenie){
			zapolnenie();
		}
		if(clean){
			clean();
		}
		canvas.beginPath();
		canvas.moveTo(x_s, y_h);
		canvas.lineTo(x_s2, y_h1);
		canvas.lineTo(x_s2, y_h2);
		canvas.lineTo(x_s1, y_h2);
		canvas.lineTo(x_s1, y_h1);
		canvas.closePath();
		canvas.fill();
		canvas.fillRect(49, 374, 7.6, vc);
		canvas.setFillStyle(new Color("rgba(255,0,0,1)"));
		canvas.fillRect(28, 275, 46, osad);
		timer.schedule(time);
	}
	
	private void clean() {
		x_s1=x_s2=x_s;
		y_h2=y_h1=y_h;
		sliv=false;
		zapolnenie=false;
		clean=false;
		waterEnd=false;
		fullFilt=false;
		osad=0;
		vc=0;
	}

	private void sliv() {
		if(y_h2<167)
		{
			y_h2+=1*speedBak;
		}else if (x_s2>53)
		{
			x_s1+=2*speedBak;
			x_s2-=2*speedBak;
			y_h1 = (x_s1 - 22)*(181-167)/31 + 167;
			y_h2=y_h1;
		}
		else
		{
			x_s1=x_s2=x_s;
			y_h2=y_h1=y_h;
			sliv=false;
			waterEnd=true;
			waterFull=false;
			fullFilt=true;
		}
	}

	private void zapolnenie() {
		waterFull=true;
		if (x_s2<81)
		{
			x_s1-=2*speedBak;
			x_s2+=2*speedBak;
			y_h1 = (x_s1 - 22)*(181-167)/31 + 167;
			y_h2=y_h1;
		}
		else if(y_h2>121)
		{
			x_s1=22;
			x_s2=81;
			y_h1=167;
			y_h2-=1*speedBak;
		}
		else
		{
			y_h2=121;
			zapolnenie=false;
		}
	}

	public void stopDemo() {
		timer.cancel();
	}

	public boolean isMotor() {
		return motor;
	}

	public void setMotor(boolean motor) {
		this.motor = motor;
	}

	public boolean isZapolnenie() {
		return zapolnenie;
	}

	public void setZapolnenie(boolean zapolnenie) {
		this.zapolnenie = zapolnenie;
	}

	public boolean isSliv() {
		return sliv;
	}

	public void setSliv(boolean sliv) {
		this.sliv = sliv;
	}

	public boolean isClean() {
		return clean;
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}

	public boolean isFullFilt() {
		return fullFilt;
	}
}
