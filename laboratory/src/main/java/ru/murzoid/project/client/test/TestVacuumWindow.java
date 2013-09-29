package ru.murzoid.project.client.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.murzoid.project.client.LabDesctop;
import ru.murzoid.project.client.vacuum.LabVacuumWindow;
import ru.murzoid.project.shared.TestTemplate;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TestVacuumWindow extends Window {

	protected VerticalPanel vp;
	protected final TestServiceAsync vacuumTest = GWT.create(TestService.class);
	protected Button start;
	protected String user;
	protected List<TestTemplate> questions=null;
	protected List<Integer> quest=null;
	private HashMap<Long, CheckBox> mapCheckBox;
	private Map<Long, List<Long>> answer;

	public TestVacuumWindow(LabDesctop lDesktop, String user) {
		super();
		this.setId("TestVacuum");
		vp = new VerticalPanel();
		vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		start = new Button("Начать тест", new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				vacuumTest.start(new AsyncCallback<List<TestTemplate>>() {


					@Override
					public void onSuccess(List<TestTemplate> ques) {
						questions=ques;
						quest=new ArrayList<Integer>();
						answer=new HashMap<Long, List<Long>>();
						for(int i=0;i<questions.size();i++){
							quest.add(i);
						}
						nextQuest();
					}

					@Override
					public void onFailure(Throwable caught) {
						Info.display("Error", "problem with start test");
					}
				});
			}
		});
		initStart();
		lDesktop.addShortcut("Тест по вакуум-фильтру", "testfilterIcon", this);
		lDesktop.addMenuItem("Тест по вакуум-фильтру", "testfilter", this);
		this.setResizable(false);
		this.setHeight(380);
		this.setWidth(600);
		vp.setPixelSize(600, 350);
//		Width("600");
//		vp.setHeight("350");
		this.add(vp);
		this.user=user;
	}

	protected void initStart() {
		vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vp.add(start);
	}

	protected void nextQuest() {
		if(quest.isEmpty()){
			stop();
		}
		TestTemplate result=questions.get(quest.get(0));
		vp.clear();
		this.setWidth(600);
		vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		Widget w1=getQuestionPanel(result.getQuestion(), result.getImage());
		vp.add(w1);
		Widget w2=getAnswersPanel(result.getAnswers());
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vp.add(w2);
		Widget w3=createToolbar();
		vp.add(w3);
	}

	public Widget getQuestionPanel(String quest, String img){
		HorizontalPanel hp=new HorizontalPanel();
		hp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		if(img!=null && !img.trim().equals("")){
			Image image=new Image(img);
			image.setPixelSize(300, 300);
			hp.add(image);
		}
		HTML html=new HTML(quest);
//		html.setPixelSize(width, height)Width("600");
		hp.add(html);
		hp.setPixelSize(600, 300);
		return hp;
	}
	
	public Widget getAnswersPanel(Map<Long, String> map) {
		// Create a Flow Panel
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.ensureDebugId("cwFlowPanel");
		mapCheckBox=new HashMap<Long, CheckBox>();
		// Add some content to the panel
		for (Long id:map.keySet()) {
			CheckBox checkbox = new CheckBox(map.get(id));
			checkbox.addStyleName("cw-FlowPanel-checkBox");
			mapCheckBox.put(id, checkbox);
			flowPanel.add(checkbox);
		}
		flowPanel.setWidth("600");
		flowPanel.setHeight("50");
		// Return the content
		return flowPanel;
	}

	protected void stop() {
		vacuumTest.stop(user, answer, new AsyncCallback<Double>() {
			
			@Override
			public void onSuccess(Double result) {
				initStop(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Error", "problem with stop");
			}
		});
	}

	protected void initStop(Double result) {
		vp.clear();
		vp.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		HTML html=new HTML("Ваш результат теста составляет " + result + "%");
		vp.add(html);
		HorizontalPanel hp=new HorizontalPanel();
		hp.add(start);
		if(result>90){
			HTML htmlT=new HTML("Вы прошли тест, можете приступать к лабораторной работе.");
			start.setText("Повторить тест");
			vp.add(htmlT);
			Button but = new Button("Начать работу", new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent ce) {
					Window w=new LabVacuumWindow(user);
					w.show();
				}
			});
			hp.add(but);
		} else {
			HTML htmlF=new HTML("Вы не прошли тест, попробуйте еще раз."+
		"<br>Но сначала прочитайте методичку доступную <a href=\"site/main.htm\" target=\"_blank\">тут</a>");
			
			vp.add(htmlF);
		}
		vp.add(hp);
	}

	
	protected ToolBar createToolbar() {
		ToolBar toolBar = new ToolBar();
		toolBar.setAlignment(HorizontalAlignment.CENTER);
		Button add = new Button("Пропустить вопрос");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				Integer tmp=quest.get(0);
				quest.remove(0);
				quest.add(tmp);
				nextQuest();
			}
	
		});
		toolBar.add(add);
		Button refresh=new Button("Останосить тест", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				stop();
			}
		});
		toolBar.add(refresh);
		Button reset=new Button("Ответить", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				List<Long> listAnswer=new ArrayList<Long>();
				for(Long key:mapCheckBox.keySet()){
					if(mapCheckBox.get(key).getValue()){
						listAnswer.add(key);
					}
				}
				answer.put(questions.get(quest.get(0)).getId(), listAnswer);
				
				if(quest.size()<=1){
					stop();
				} else {
					quest.remove(0);
					nextQuest();
				}
			}
		});
		toolBar.add(reset);
		return toolBar;
	}
}
