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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Widget;


public class QuestionsManage extends EditorTableWindow{

	private AnswerManage am1;
	private AnswerManage am2;

	public QuestionsManage(LabDesctop lDesktop) {
		super(TablesEnum.Questions);
		this.setId("Questions");
//		lDesktop.addShortcut("Тестовые Вопросы", "questionsS", this);
		lDesktop.addMenuItem("Questions", "questionsMI", this);
		this.setHeading("Questions");
		this.setWidth(610);
		grid.setWidth(590);
		initialPopUp1();
		initialPopUp2();
	}

	public QuestionsManage(ToolBar toolbar, boolean select, boolean simple) {
		super(TablesEnum.Questions, toolbar, select, simple);
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
		bsm.set("gruppa","default");
		bsm.set("question","");
		bsm.set("variants",new ArrayList<Long>());
		bsm.set("variants_true",new ArrayList<Long>());
		bsm.set("image",new String(""));
		return bsm;
	}

	@Override
	protected List<ColumnConfig> getColumns() {

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
		column.setId("gruppa");
		column.setHeader("Gruppa");
		column.setWidth(100);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("question");
		column.setHeader("Question");
		column.setWidth(200);

		text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("variants");
		column.setHeader("Variants");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(120);
		column.setNumberFormat(NumberFormat.getDecimalFormat());

		GridCellRenderer<BaseModelData> buttonRenderer = new GridCellRenderer<BaseModelData>() {

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

				Button b = new Button("Варианты ответов", new SelectionListener<ButtonEvent>() {
							@SuppressWarnings("unchecked")
							@Override
							public void componentSelected(ButtonEvent ce) {
								
								 // Reposition the popup relative to the button
					            Widget source = (Widget) ce.getSource();
					            int left = source.getAbsoluteLeft() + 10;
					            int top = source.getAbsoluteTop() + 10;
					            current=model;
					            am1.setPosition(left, top);
					            am1.getSm().deselectAll();
					            for(BaseModelData bsm: am1.getStore().getModels()){
					            	for(Long id: (List<Long>)model.get("variants")){
					            		if(((Long)bsm.get("id"))==id){
					            			am1.getSm().select(bsm, true);
					            		}
					            	}
					            }
					            am1.setVisible(true);
							}
						});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip("нажмите чтобы изменить варианты ответов");

				return b;
			}
		};

		column.setRenderer(buttonRenderer);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("variants_true");
		column.setHeader("Variants_true");
		column.setWidth(100);
		
		GridCellRenderer<BaseModelData> buttonRenderer2 = new GridCellRenderer<BaseModelData>() {

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

				Button b = new Button("Правильные варианты", new SelectionListener<ButtonEvent>() {
							@SuppressWarnings("unchecked")
							@Override
							public void componentSelected(ButtonEvent ce) {
								 // Reposition the popup relative to the button
					            Widget source = (Widget) ce.getSource();
					            int left = source.getAbsoluteLeft() + 10;
					            int top = source.getAbsoluteTop() + 10;
					            current=model;
					            // Show the popup
					            am2.setPosition(left, top);
					            am2.getSm().deselectAll();
					            for(BaseModelData bsm: am2.getStore().getModels()){
					            	for(Long id: (List<Long>)model.get("variants_true")){
					            		if(((Long)bsm.get("id"))==id){
					            			am2.getSm().select(bsm, true);
					            		}
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

		column.setRenderer(buttonRenderer2);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("image");
		column.setHeader("URL image");
		column.setWidth(100);

		text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);
		return configs;
	}

	public void initialPopUp1(){
		am1=new AnswerManage(createToolbar1(), true, false);
		am1.setAutoHeight(true);
		am1.setAutoWidth(true);
		am1.setAutoHide(true);
		am1.hide();
	}

	public void initialPopUp2(){
		am2=new AnswerManage(createToolbar2(), true, false);
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
				List<Long> list=new ArrayList<Long>();
				for(BaseModelData bsm:am1.getSm().getSelectedItems()){
					list.add((Long) bsm.get("id"));
				}
				current.set("variants", list);
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
				List<Long> list=new ArrayList<Long>();
				for(BaseModelData bsm:am2.getSm().getSelectedItems()){
					list.add((Long) bsm.get("id"));
				}
				current.set("variants_true", list);
				am2.hide();
			}
		});
		toolBar.add(save);
		return toolBar;
	}

}
