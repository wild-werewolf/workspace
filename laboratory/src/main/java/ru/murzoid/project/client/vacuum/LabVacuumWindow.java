package ru.murzoid.project.client.vacuum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.murzoid.project.client.LabDesctop;
import ru.murzoid.project.shared.VacuumData;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class LabVacuumWindow extends Window {

	private GWTCanvas canvas;
//	private HorizontalPanel layout;
	
	protected final VacuumServiceAsync vacuumTest = GWT.create(VacuumService.class);
	private VerticalPanel vp;
	private HorizontalPanel hp;
	private LabVacuumCanvas currentDemo;
	private VacuumData vacuumData;
	
	protected ListStore<BaseModelData> store = new ListStore<BaseModelData>();;
	protected Grid<BaseModelData> grid;
	private HTML html;
	private ListStore<BaseModelData> osadokStore;
	private ListStore<BaseModelData> peregStore;
	private FormPanel simple;

	private TextField<String> gcF;

	private TextField<String> xcF;

	private TextField<String> wocF;

	private TextField<String> tF;

	private TextField<String> deltaPF;
	private ComboBox<BaseModelData> comboPereg;
	private ComboBox<BaseModelData> comboOsadok;

	public LabVacuumWindow(LabDesctop lDesktop, String username) {
		super();
		initLab(username);
		lDesktop.addShortcut("Вакуум-фильтр", "vacuumfilterIcon", this);
		lDesktop.addMenuItem("Вакуум-фильтр", "vacuumfilter", this);
	}
	
	public LabVacuumWindow(String username) {
		super();
		initLab(username);
	}

	private void initLab(String username) {
		this.setId("LabVacuum");
		int height = 430;
		vacuumTest.getVacuumData(username, new AsyncCallback<VacuumData>() {

			@Override
			public void onSuccess(VacuumData result) {
				for(Map<String,Serializable> osad:result.getOsadokLT()){
					BaseModelData base=new BaseModelData();
					Map<String, Object> map=new HashMap<String, Object>();
					map.putAll(osad);
					base.setProperties(map);
					osadokStore.add(base);
				}
				for(Map<String,Serializable> pereg:result.getPeregLT()){
					BaseModelData base=new BaseModelData();
					Map<String, Object> map1=new HashMap<String, Object>();
					map1.putAll(pereg);
					base.setProperties(map1);
					peregStore.add(base);
				}
				vacuumData=result;				
				initialForm();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display("Error", "Не возможно запустить тест сервер вернул ошибку");
				disable();
			}
		});
		vp = new VerticalPanel();
		vp.add(createToolbar1());
		int heightCanvas=430;
		int widthCanvas=300;
		hp=new HorizontalPanel();
		VerticalPanel verP=new VerticalPanel();
		canvas = new GWTCanvas(widthCanvas, heightCanvas);
		currentDemo = new LabVacuumCanvas(canvas);
		hp.add(canvas);

		html = new HTML();
		html.setHeight("50");
		verP.add(html);
		verP.add(getTable(heightCanvas - 50));
		hp.add(verP);
		hp.add(createForm1(heightCanvas));
		vp.add(hp);
		currentDemo.setClean(true);
	    currentDemo.drawDemo(widthCanvas, heightCanvas);
	    setHeading("Lab Vacuum-Filter");
//	    this.setWidth(width);
	    this.setAutoWidth(true);
	    this.setHeight(height);
	    this.add(vp);
	}
	  


	private FormPanel createForm1(int heightCanvas) {
		simple = new FormPanel();
		simple.setHeading("Lab Vacuum-Filter2");
		simple.setFrame(true);
		simple.setHeight(heightCanvas);
		simple.setWidth(300);
		simple.setFieldWidth(120);
		FormData formData = new FormData("-10");
		osadokStore = new ListStore<BaseModelData>();
		comboOsadok = new ComboBox<BaseModelData>();
		comboOsadok.setFieldLabel("Осадок");
		comboOsadok.setEmptyText("Выберите осадок");
		comboOsadok.setDisplayField("name");
		comboOsadok.setStore(osadokStore);
		comboOsadok.setTypeAhead(true);
		comboOsadok.setTriggerAction(TriggerAction.ALL);
		comboOsadok.setEditable(false);
		comboOsadok.setEnabled(false);
		simple.add(comboOsadok, formData);
		
		peregStore = new ListStore<BaseModelData>();
		comboPereg = new ComboBox<BaseModelData>();
		comboPereg.setFieldLabel("Перегородка");
		comboPereg.setEmptyText("Выберите перегородку");
		comboPereg.setDisplayField("name");
		comboPereg.setStore(peregStore);
		comboPereg.setTypeAhead(true);
		comboPereg.setTriggerAction(TriggerAction.ALL);
		comboPereg.setEditable(false);
		comboPereg.setEnabled(false);
		simple.add(comboPereg, formData);

		gcF= new TextField<String>();
		gcF.setFieldLabel("Количество водной суспензии");
		gcF.setEnabled(false);
		simple.add(gcF, formData);
		
		xcF = new TextField<String>();
		xcF.setFieldLabel("концентрация суспензии");
		xcF.setEnabled(false);
		simple.add(xcF, formData);

		wocF= new TextField<String>();
		wocF.setFieldLabel("влажность осадка");
		wocF.setEnabled(false);
		simple.add(wocF, formData);

		tF = new TextField<String>();
		tF.setFieldLabel("температура суспензии");
		tF.setEnabled(false);
		simple.add(tF, formData);

		deltaPF = new TextField<String>();
		deltaPF.setFieldLabel("перепад давлений");
		deltaPF.setEnabled(false);
		simple.add(deltaPF, formData);

		return simple;
	}

	private void initialForm(){
		gcF.setValue(Double.toString(vacuumData.getGc()));
		xcF.setValue(Double.toString(vacuumData.getXc()));
		wocF.setValue(Double.toString(vacuumData.getWoc()));
		tF.setValue(Integer.toString(vacuumData.getTemper()));
		deltaPF.setValue(Double.toString(vacuumData.getDeltap()));
		for(BaseModelData list: osadokStore.getModels()){
			if(vacuumData!=null && vacuumData.getOsadok()!=null && vacuumData.getOsadok().equalsIgnoreCase(list.get("name").toString())){
//				List<BaseModelData> select=new ArrayList<BaseModelData>();
				comboOsadok.setValue(list);
			}
		}
		for(BaseModelData list: peregStore.getModels()){
			if(vacuumData!=null && vacuumData.getPeregorodka()!=null &&  vacuumData.getPeregorodka().equalsIgnoreCase(list.get("name").toString())){
				comboPereg.setValue(list);
			}
		}
		
		render(simple.getElement());
	}
	
	protected ToolBar createToolbar1() {
		ToolBar toolBar = new ToolBar();
		Button reset=new ToggleButton("Мотор", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				currentDemo.setMotor(!currentDemo.isMotor());
			}
		});
		toolBar.add(reset);
		Button zapol=new Button("Заполнить бак", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(!currentDemo.isSliv() && !currentDemo.isZapolnenie()){
					currentDemo.setZapolnenie(true);
				}
			}
		});
		toolBar.add(zapol);
		Button sliv=new Button("Начало фильтрации", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(!currentDemo.isSliv() && !currentDemo.isZapolnenie() && !currentDemo.isFullFilt()){
					currentDemo.setSliv(true);
					html.setText("Высота осадка равна "+vacuumData.getH());
					html.setVisible(true);
					updateTable();			
				}
			}
		});
		toolBar.add(sliv);
		Button clean=new Button("Очистка", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				currentDemo.setClean(true);
				store.removeAll();
				html.setVisible(false);
				render(canvas.getElement());
				render(grid.getElement());
			}
		});
		toolBar.add(clean);
		return toolBar;
	}
	
	protected ToolBar createToolbar2() {
		ToolBar toolBar = new ToolBar();
		return toolBar;
	}


	private void updateTable() {
		store.removeAll();
		store.add(getBaseModelData(vacuumData.getTime1(), 0.2));
		store.add(getBaseModelData(vacuumData.getTime2(), 0.4));
		store.add(getBaseModelData(vacuumData.getTime3(), 0.6));
		store.add(getBaseModelData(vacuumData.getTime4(), 0.8));
		store.add(getBaseModelData(vacuumData.getTime5(), 1.0));
		store.add(getBaseModelData(vacuumData.getTime6(), 1.2));
		store.add(getBaseModelData(vacuumData.getTime7(), 1.4));
		store.add(getBaseModelData(vacuumData.getTime8(), 1.6));
		store.add(getBaseModelData(vacuumData.getTime9(), 1.8));
		store.add(getBaseModelData(vacuumData.getTime10(), 2.0));
		store.add(getBaseModelData(vacuumData.getTime11(), 2.2));
		store.add(getBaseModelData(vacuumData.getTime12(), 2.4));
		store.add(getBaseModelData(vacuumData.getTime13(), 2.6));
		store.add(getBaseModelData(Math.round(vacuumData.getTimef()), vacuumData.getVf()));
	}
	
	private Grid<BaseModelData> getTable(int height){
		List<ColumnConfig> configs = getColumns();
		ColumnModel cm = new ColumnModel(configs);
		grid = new Grid<BaseModelData>(store, cm);
		grid.setBorders(true);
		grid.setHeight(height);
		this.render(grid.getElement());
		return grid;
	}

	protected List<ColumnConfig> getColumns() {


		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();

		column = new ColumnConfig();
		column.setId("id");
		column.setHeader("id");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(35);
		column.setNumberFormat(NumberFormat.getFormat("##"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("time");
		column.setHeader("Time");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(90);
		column.setNumberFormat(NumberFormat.getFormat("#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("Value");
		column.setHeader("Value");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(70);
		column.setNumberFormat(NumberFormat.getFormat("#.##"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("udel");
		column.setHeader("Udel");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(90);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("tq");
		column.setHeader("t/q");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(90);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		return configs;
	}

	protected BaseModelData getBaseModelData(long time, double val) {
		BaseModelData bsm = new BaseModelData();
		long id = 0;
		for (BaseModelData tmp : store.getModels()) {
			long curr = tmp.get("id");
			if (id < curr) {
				id = curr;
			}
		}
		id++;
		bsm.set("id",id);
		bsm.set("time",time);
		bsm.set("Value",val);
		double q=val/0.0064;
		bsm.set("udel",q);
		double tq=time/q;
		bsm.set("tq",tq);
		return bsm;
	}
}
