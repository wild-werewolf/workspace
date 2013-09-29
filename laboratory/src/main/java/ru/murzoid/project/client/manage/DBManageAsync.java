package ru.murzoid.project.client.manage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import ru.murzoid.project.shared.DBManageException;
import ru.murzoid.project.shared.TablesEnum;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DBManageAsync {

	void load(TablesEnum table, AsyncCallback<List<Map<String, Serializable>>> callback) throws DBManageException;
	void save(TablesEnum table, List<Map<String, Serializable>> list, AsyncCallback<String> callback) throws DBManageException;
	void refresh(TablesEnum table, AsyncCallback<String> callback);
}
