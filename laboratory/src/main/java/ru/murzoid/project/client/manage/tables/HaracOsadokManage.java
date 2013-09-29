package ru.murzoid.project.client.manage.tables;

import java.util.ArrayList;
import java.util.List;

import ru.murzoid.project.client.LabDesctop;
import ru.murzoid.project.client.manage.EditorTableWindow;
import ru.murzoid.project.shared.TablesEnum;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.NumberFormat;

public class HaracOsadokManage extends EditorTableWindow {

	public HaracOsadokManage(LabDesctop lDesktop) {
		super(TablesEnum.HaracOsadok);
		this.setId("HaracOsadok");
//	    lDesktop.addShortcut("Характеристика Осадка", "haracOsadokS", this);
	    lDesktop.addMenuItem("Хар. Осадка", "haracOsadokMI",this);
		this.setWidth(610);
		grid.setWidth(590);
		this.setHeading("HaracOsadoc");
	}

	public HaracOsadokManage(ToolBar toolbar, boolean select, boolean simple) {
		super(TablesEnum.HaracOsadok, toolbar, select, simple);
		this.setWidth(610);
		grid.setWidth(590);
	}

	protected BaseModelData getBaseModelData(){
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
		bsm.set("udelsopr", 0.0);
		bsm.set("plotverfaz", 0);
		return bsm;
	}
	
	protected List<ColumnConfig> getColumns(){
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
		column.setId("name");
		column.setHeader("Name");
		column.setWidth(190);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("udelsopr");
		column.setHeader("UdelSoprotiv");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(195);
		column.setNumberFormat(NumberFormat.getFormat("#.###E00"));
		column.setEditor(new CellEditor(new NumberField()));

		configs.add(column);

		column = new ColumnConfig();
		column.setId("plottverfaz");
		column.setHeader("PlottverdPhase");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(195);
		column.setNumberFormat(NumberFormat.getDecimalFormat());
		column.setEditor(new CellEditor(new NumberField()));
		configs.add(column);
		return configs;
	}
}
