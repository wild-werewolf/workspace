package ru.murzoid.project.client.manage.tables;

import java.util.ArrayList;
import java.util.List;

import ru.murzoid.project.client.LabDesctop;
import ru.murzoid.project.client.MD5.MD5Calculator;
import ru.murzoid.project.client.manage.EditorTableWindow;
import ru.murzoid.project.shared.TablesEnum;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.NumberFormat;

public class UsersManage extends EditorTableWindow {

	public UsersManage(LabDesctop lDesktop) {
		super(TablesEnum.Users);
		this.setId("Users");
		lDesktop.addShortcut("Users", "usersS",	this);
		lDesktop.addMenuItem("Users", "usersMI", this);
		this.setWidth(610);
		grid.setWidth(590);
		this.setHeading("Users");
	}

	public UsersManage(ToolBar toolbar, boolean select, boolean simple) {
		super(TablesEnum.Users, toolbar, select, simple);
		this.setWidth(610);
		grid.setWidth(590);
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
		bsm.set("id", id);
		bsm.set("name", "default");
		bsm.set("login", "default");
		bsm.set("password", "");
		bsm.set("gruppa", "unknown");
		bsm.set("permission", "student");
		return bsm;
	}

	@Override
	protected List<ColumnConfig> getColumns() {

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
		column.setId("name");
		column.setHeader("Name");
		column.setWidth(100);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("login");
		column.setHeader("Login");
		column.setWidth(100);

		text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("password");
		column.setHeader("Password");
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

				Button b = new Button("РЎРјРµРЅР° РїР°СЂРѕР»СЏ", new SelectionListener<ButtonEvent>() {
							@Override
							public void componentSelected(ButtonEvent ce) {
						        final MessageBox box = MessageBox.prompt("РџР°СЂРѕР»СЊ", "РџРѕР¶Р°Р»СѓР№СЃС‚Р° РІРІРµРґРёС‚Рµ РЅРѕРІС‹Р№ РїР°СЂРѕР»СЊ:");  
						        box.addCallback(new Listener<MessageBoxEvent>() {  
						        	public void handleEvent(MessageBoxEvent be) {
						        		MD5Calculator calculatorMD5 = new MD5Calculator();
						        		model.set("password", calculatorMD5.calculate(be.getValue()));
						          }  
						        });  
							}
						});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip("РЅР°Р¶РјРёС‚Рµ С‡С‚РѕР±С‹ СЃРјРµРЅРёС‚СЊ СЋР·РµСЂСѓ РїР°СЂРѕР»СЊ");

				return b;
			}
		};

		column.setRenderer(buttonRenderer);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("gruppa");
		column.setHeader("Gruppa");
		column.setWidth(100);

		text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);

		column = new ColumnConfig();
		column.setId("permission");
		column.setHeader("Permissions");
		column.setWidth(100);

		text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);
		return configs;
	}

}
