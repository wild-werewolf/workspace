package ru.murzoid.project.client.manage.tables;

import java.util.ArrayList;
import java.util.List;

import ru.murzoid.project.client.LabDesctop;
import ru.murzoid.project.client.manage.EditorTableWindow;
import ru.murzoid.project.shared.TablesEnum;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Widget;

public class LabWorkManage extends EditorTableWindow{

	private UsersManage am1;
	private LabVariantManage am2;
	private Button currB;
	
	public LabWorkManage(LabDesctop lDesktop) {
		super(TablesEnum.LabWork);
		this.setId("LabWork");
		lDesktop.addMenuItem("LabWork", "labWorkMI", this);
		this.setHeading("LabWork");
		this.setWidth(610);
		grid.setWidth(590);
		initialPopUp1();
		initialPopUp2();
	}
	
	public LabWorkManage(ToolBar toolbar, boolean select, boolean simple) {
		super(TablesEnum.LabWork, toolbar, select, simple);
		this.setWidth(610);
		grid.setWidth(590);
		initialPopUp1();
		initialPopUp2();
	}

	@Override
	protected BaseModelData getBaseModelData() {
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
		bsm.set("idUser",new Long(0));
		bsm.set("idLabVariant",new Long(0));	
		bsm.set("Apol",0);
		bsm.set("Bpol",0);
		bsm.set("hos",0);
		bsm.set("rezultTest",false);
		bsm.set("V02",0);
		bsm.set("V04",0);
		bsm.set("V06",0);
		bsm.set("V08",0);
		bsm.set("V10",0);
		bsm.set("V12",0);
		bsm.set("V14",0);
		bsm.set("V16",0);
		bsm.set("V18",0);
		bsm.set("V20",0);
		bsm.set("V22",0);
		bsm.set("V24",0);
		bsm.set("V26",0);
		bsm.set("V28",0);
		return bsm;
	}

	@Override
	protected List<ColumnConfig> getColumns() {


		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
			
		column = new ColumnConfig();
		column.setId("idUser");
		column.setHeader("ID User");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(200);
		column.setNumberFormat(NumberFormat.getDecimalFormat());
		
		GridCellRenderer<BaseModelData> buttonRenderer3 = new GridCellRenderer<BaseModelData>() {

			private boolean init;

			public Object render(final BaseModelData model, final String property,
					ColumnData config, final int rowIndex, final int colIndex,
					ListStore<BaseModelData> store1, Grid<BaseModelData> grid) {
				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize, new Listener<GridEvent<BaseModelData>>() {
						public void handleEvent(GridEvent<BaseModelData> be) {
							for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
								if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
									&& be.getGrid().getView().getWidget(i,be.getColIndex()) instanceof BoxComponent) {
										((BoxComponent) be.getGrid().getView().getWidget(i,be.getColIndex())).setWidth(be.getWidth() - 10);
								}
							}
						}
					});
				}
				
				String name="Не назначен";
	            for(BaseModelData bsm: am1.getStore().getModels()){
	            	if(((Long)bsm.get("id"))==(Long)model.get(property)){
	            		name=bsm.get("gruppa")+"\\"+bsm.get("name");
            			break;
	            	}
	            }

				final Button b = new Button(name, new SelectionListener<ButtonEvent>() {
							@Override
							public void componentSelected(ButtonEvent ce) {
								
								 // Reposition the popup relative to the button
					            Widget source = (Widget) ce.getSource();
					            int left = source.getAbsoluteLeft() + 10;
					            int top = source.getAbsoluteTop() + 10;
					            current=model;
					            currB=ce.getButton();
					            am1.setPosition(left, top);
					            am1.getSm().deselectAll();
					            for(BaseModelData bsm: am1.getStore().getModels()){
					            	System.out.println(model.get(property));
					            	if((bsm.get("id"))==model.get(property)){
				            			am1.getSm().select(bsm, true);
				            			break;
					            	}
					            }
					            am1.setVisible(true);
							}
						});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip("нажмите чтобы изменить назначенного пользователя");
				return b;
			}
		};

		column.setRenderer(buttonRenderer3);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("idLabVariant");
		column.setHeader("ID Lab Variant");
		column.setWidth(100);
		
		GridCellRenderer<BaseModelData> buttonRenderer4 = new GridCellRenderer<BaseModelData>() {

			private boolean init;

			public Object render(final BaseModelData model, final String property,
					ColumnData config, final int rowIndex, final int colIndex,
					ListStore<BaseModelData> store1, Grid<BaseModelData> grid) {
				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize, new Listener<GridEvent<BaseModelData>>() {
						public void handleEvent(GridEvent<BaseModelData> be) {
							for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
								if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
									&& be.getGrid().getView().getWidget(i,be.getColIndex()) instanceof BoxComponent) {
										((BoxComponent) be.getGrid().getView().getWidget(i,be.getColIndex())).setWidth(be.getWidth() - 10);
								}
							}
						}
					});
				}
				
				String name="Не назначен";
	            for(BaseModelData bsm: am2.getStore().getModels()){
	            	if((bsm.get("id"))==model.get("idLabVariant")){
            			name=bsm.get("name");
            			break;
	            	}
	            }

				Button b = new Button(name, new SelectionListener<ButtonEvent>() {
							@Override
							public void componentSelected(ButtonEvent ce) {
								 // Reposition the popup relative to the button
					            Widget source = (Widget) ce.getSource();
					            int left = source.getAbsoluteLeft() + 10;
					            int top = source.getAbsoluteTop() + 10;
					            current=model;
					            currB=ce.getButton();
					            // Show the popup
					            am2.setPosition(left, top);
					            am2.getSm().deselectAll();
					            for(BaseModelData bsm: am2.getStore().getModels()){
					            	System.out.println(model.get(property));
					            	if((bsm.get("id"))==model.get(property)){
					            		am2.getSm().select(bsm, true);
					            		break;
					            	}
					            }
					            am2.setVisible(true);
							}
						});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip("нажмите чтобы изменить правильные варианты ответов");

				return b;
			}
		};

		column.setRenderer(buttonRenderer4);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("Apol");
		column.setHeader("Apol");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("Bpol");
		column.setHeader("Bpol");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("hos");
		column.setHeader("hos");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V02");
		column.setHeader("V02");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V04");
		column.setHeader("V04");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V06");
		column.setHeader("V06");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V08");
		column.setHeader("V08");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V10");
		column.setHeader("V10");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V12");
		column.setHeader("V12");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V14");
		column.setHeader("V14");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V16");
		column.setHeader("V16");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V18");
		column.setHeader("V18");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V20");
		column.setHeader("V20");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V22");
		column.setHeader("V22");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V24");
		column.setHeader("V24");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V26");
		column.setHeader("V26");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("V28");
		column.setHeader("V28");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		

		return configs;
	}


	public void initialPopUp1(){
		am1=new UsersManage(createToolbar1(), true, true);
		am1.setAutoHeight(true);
		am1.setAutoWidth(true);
		am1.setAutoHide(true);
		am1.hide();
	}

	public void initialPopUp2(){
		am2=new LabVariantManage(createToolbar2(), true, true);
		am2.setAutoHeight(true);
		am2.setAutoWidth(true);
		am2.setAutoHide(true);
		am2.hide();
	}
	
	protected ToolBar createToolbar1() {ToolBar toolBar = new ToolBar();
		Button reset=new Button("Reset Select", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				am1.getSm().deselectAll();
			}
		});
		toolBar.add(reset);
		Button save=new Button("Save Select", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				Long value=null;				
				for(BaseModelData bsm:am1.getSm().getSelectedItems()){
					value=bsm.get("id");
					currB.setText((String) bsm.get("gruppa")+"\\"+bsm.get("name"));
					break;
				}
				current.set("idUser", value);
				am1.hide();
			}
		});
		toolBar.add(save);
		return toolBar;
	}
	
	protected ToolBar createToolbar2() {ToolBar toolBar = new ToolBar();
		Button reset=new Button("Reset Select", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				am2.getSm().deselectAll();
			}
		});
		toolBar.add(reset);
		Button save=new Button("Save Select", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				Long value=null;
				for(BaseModelData bsm:am2.getSm().getSelectedItems()){
					value=(Long) bsm.get("id");
					currB.setText((String) bsm.get("name"));
					break;
				}
				current.set("idLabVariant", value);
				am2.hide();
			}
		});
		toolBar.add(save);
		return toolBar;
	}

}
