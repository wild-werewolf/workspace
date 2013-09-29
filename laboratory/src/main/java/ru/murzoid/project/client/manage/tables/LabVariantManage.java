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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Widget;

public class LabVariantManage extends EditorTableWindow{

	private HaracOsadokManage am1;
	private HaracPeregManage am2;
	private Button currB;
	
	public LabVariantManage(LabDesctop lDesktop) {
		super(TablesEnum.LabVariant);
		this.setId("LabVariant");
		lDesktop.addMenuItem("LabVarient �", "labVariantMI", this);
		this.setHeading("LabVariant �");
		this.setWidth(610);
		grid.setWidth(590);
		initialPopUp1();
		initialPopUp2();
	}

	public LabVariantManage(ToolBar toolbar, boolean select, boolean simple) {
		super(TablesEnum.LabVariant, toolbar, select, simple);
		this.setWidth(610);
		grid.setWidth(590);
		initialPopUp1();
		initialPopUp2();
	}
	
	@Override
	protected BaseModelData getBaseModelData() {
		BaseModelData bsm = new BaseModelData();
		Long id = new Long(0);
		for (BaseModelData tmp : store.getModels()) {
			long curr = tmp.get("id");
			if (id < curr) {
				id = curr;
			}
		}
		id++;
		bsm.set("id",id);
		bsm.set("name","default");
		bsm.set("GC",3);
		bsm.set("XC",0.03);
		bsm.set("WOC",0.1);
		bsm.set("temper",20);
		bsm.set("deltap",0.4);
		bsm.set("id_harac_osadok",new Long(-1));
		bsm.set("id_harac_pereg",new Long(-1));
		return bsm;
	}

	@Override
	protected List<ColumnConfig> getColumns() {


		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
		
		column = new ColumnConfig();
		column.setId("GC");
		column.setHeader("GC");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("name");
		column.setHeader("Name");
		column.setWidth(100);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("XC");
		column.setHeader("XC");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("WOC");
		column.setHeader("WDC");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		

		column = new ColumnConfig();
		column.setId("temper");
		column.setHeader("Temper");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		

		column = new ColumnConfig();
		column.setId("deltap");
		column.setHeader("Deltap");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(95);
		column.setNumberFormat(NumberFormat.getFormat("#.#####"));
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("id_harac_osadok");
		column.setHeader("ID_harac_osad");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(100);
		column.setNumberFormat(NumberFormat.getDecimalFormat());
		
		GridCellRenderer<BaseModelData> buttonRenderer3 = new GridCellRenderer<BaseModelData>() {

			private boolean init;

			public Object render(final BaseModelData model, String property,
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
					            am1.setPosition(left, top);
					            am1.getSm().deselectAll();
					            for(BaseModelData bsm: am1.getStore().getModels()){
					            	if(((Long)bsm.get("id"))==(Long)model.get("id_harac_osadok")){
				            			am1.getSm().select(bsm, true);
				            			break;
					            	}
					            }
					            am1.setVisible(true);
							}
						});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip("нажмите чтобы изменить осадок");
				return b;
			}
		};

		column.setRenderer(buttonRenderer3);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("id_harac_pereg");
		column.setHeader("ID_harac_pereg");
		column.setWidth(100);
		
		GridCellRenderer<BaseModelData> buttonRenderer4 = new GridCellRenderer<BaseModelData>() {

			private boolean init;

			public Object render(final BaseModelData model, String property,
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
	            	if(((Long)bsm.get("id"))==(Long)model.get(property)){
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
					            	if(((Long)bsm.get("id"))==(Long)model.get("id_harac_pereg")){
					            			am2.getSm().select(bsm, true);
					            			break;
					            	}
					            }
					            am2.setVisible(true);
							}
						});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip("нажмите чтобы изменить перегородку");

				return b;
			}
		};

		column.setRenderer(buttonRenderer4);
		configs.add(column);

		return configs;
	}


	public void initialPopUp1(){
		am1=new HaracOsadokManage(createToolbar1(), true, true);
		am1.setAutoHeight(true);
		am1.setAutoWidth(true);
		am1.setAutoHide(true);
		am1.hide();
	}

	public void initialPopUp2(){
		am2=new HaracPeregManage(createToolbar2(), true, true);
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
					value=(Long) bsm.get("id");
					currB.setText((String) bsm.get("name"));
					break;
				}
				current.set("id_harac_osadok", value);
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
				current.set("id_harac_pereg", value);
				am2.hide();
			}
		});
		toolBar.add(save);
		return toolBar;
	}

}
