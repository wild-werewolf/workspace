package ru.murzoid.project.client.manage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.murzoid.project.shared.TablesEnum;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class EditorTableWindow extends Window {

	private final DBManageAsync dbManage = GWT.create(DBManage.class);
	protected HorizontalPanel hp;
	protected VerticalPanel vp;
	protected ListStore<BaseModelData> store = new ListStore<BaseModelData>();;
	protected Grid<BaseModelData> grid;
	protected TablesEnum table;
	protected CheckBoxSelectionModel<BaseModelData> sm;
	protected BaseModelData current;

	public EditorTableWindow(TablesEnum table) {
		super();
		initManage(table, null, false, false);
	}

	public EditorTableWindow(TablesEnum table, ToolBar toolbar, boolean select, boolean simple) {
		super();
		initManage(table, toolbar, select, simple);
	}
	
	private void initManage(TablesEnum table, ToolBar toolbar, boolean select, boolean simple){
		this.table=table;
		load();
		vp=new VerticalPanel();
		hp=new HorizontalPanel();
		List<ColumnConfig> configs = getColumns();
	    sm = new CheckBoxSelectionModel<BaseModelData>();
	    sm.setSelectionMode(simple ? SelectionMode.SINGLE : SelectionMode.MULTI); 
	    configs.add(0, sm.getColumn());
		ColumnModel cm = new ColumnModel(configs);
		grid = new EditorGrid<BaseModelData>(store, cm);
		grid.setBorders(true);
		grid.setHeight(300);
	    grid.addPlugin(sm);
	    grid.setSelectionModel(sm);
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		hp.add(grid);
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vp.add(createToolbar());
		vp.add(hp);
		if(select){
			vp.add(toolbar);
		}
		renderGrid();
		this.add(vp);
	}

	protected void renderGrid() {
		this.render(grid.getElement());
	}
	
	protected ToolBar createToolbar() {ToolBar toolBar = new ToolBar();
		Button add = new Button("Добавить строку");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				BaseModelData bsm=getBaseModelData();
				store.insert(bsm, 0);
				grid.reconfigure(store, new ColumnModel(getColumns()));
				render(grid.getElement());
				grid.repaint();
				
			}
	
		});
		toolBar.add(add);
		Button del=new Button("Удалить выделенное", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				for(BaseModelData bsm:sm.getSelectedItems()){
					store.remove(bsm);
				}
				render(grid.getElement());
			}
		});
		toolBar.add(del);
		Button refresh=new Button("Обновить", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				refresh();
				render(grid.getElement());
			}
		});
		toolBar.add(refresh);
		Button reset=new Button("Сброс изменений", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				grid.getStore().rejectChanges();
				render(grid.getElement());
			}
		});
		toolBar.add(reset);
		Button save=new Button("Сохранить", new SelectionListener<ButtonEvent>() {
	
			@Override
			public void componentSelected(ButtonEvent ce) {
				store.commitChanges();
				save(store.getModels());
				render(grid.getElement());
			}
		});
		toolBar.add(save);
		return toolBar;
	}

	protected abstract BaseModelData getBaseModelData();
	
	protected abstract List<ColumnConfig> getColumns();
	
	protected void load() {
		dbManage.load(table, new AsyncCallback<List<Map<String,Serializable>>>() {
			
			@Override
			public void onSuccess(List<Map<String, Serializable>> result) {
				grid.getStore().removeAll();
				for(Map<String, Serializable> map:result){
					Map<String, Object> map1=new HashMap<String, Object>();
					for(String key:map.keySet()){
						map1.put(key, map.get(key));
					}
					BaseModelData bsm=new BaseModelData(map1);
					grid.getStore().add(bsm);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				System.out.println("dbManage return failure");
			}
		});
	}

	protected void save(List<BaseModelData> bsmList) {
		List<Map<String,Serializable>> mapList=new ArrayList<Map<String,Serializable>>();
		for(BaseModelData bsm:bsmList){
			Map<String, Object> map1=bsm.getProperties();
			Map<String, Serializable> map = new HashMap<String, Serializable>();
			for(String key:map1.keySet()){
				map.put(key, (Serializable) map1.get(key));
			}
			mapList.add(map);
		}
		dbManage.save(table, mapList, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				System.out.println(""+table.name()+" saved successful");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				System.out.println("dbManage return failure");
			}
		});
	}
	
	protected void refresh() {
		dbManage.refresh(table, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				System.out.println(""+table.name()+" refresh successful");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				System.out.println("dbManage return failure, refresh failed :(");
			}
		});
		load();
	}


	public Grid<BaseModelData> getGrid() {
		return grid;
	}

	public CheckBoxSelectionModel<BaseModelData> getSm() {
		return sm;
	}

	public ListStore<BaseModelData> getStore() {
		return store;
	}

	public VerticalPanel getVp() {
		return vp;
	}

}
