package ru.murzoid.project.client.manage.tables;

import java.util.ArrayList;
import java.util.List;

import ru.murzoid.project.client.LabDesctop;
import ru.murzoid.project.client.manage.EditorTableWindow;
import ru.murzoid.project.shared.TablesEnum;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;


public class AnswerManage extends EditorTableWindow{

	public AnswerManage(LabDesctop lDesktop) {
		super(TablesEnum.Answer);
		this.setId("Answer");
		lDesktop.addMenuItem("Answers", "answerMI", this);
		this.setWidth(610);
		grid.setWidth(590);
		this.setHeading("Answers");
	}

	public AnswerManage(ToolBar toolbar, boolean select, boolean simple) {
		super(TablesEnum.Answer, toolbar, select, simple);
		this.setWidth(610);
		grid.setWidth(590);
	}

	@Override
	protected BaseModelData getBaseModelData() {BaseModelData bsm = new BaseModelData();
		long id = 0;
		for (BaseModelData tmp : store.getModels()) {
			long curr = tmp.get("id");
			if (id < curr) {
				id = curr;
			}
		}
		id++;
		bsm.set("id",id);
		bsm.set("variant","default");
		return bsm;
	}

	@Override
	protected List<ColumnConfig> getColumns() {

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
		column.setId("variant");
		column.setHeader("Answer2");
		column.setWidth(200);

		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		column.setEditor(new CellEditor(text));
		configs.add(column);

		return configs;
	}


}
