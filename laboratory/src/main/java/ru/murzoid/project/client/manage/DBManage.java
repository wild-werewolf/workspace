package ru.murzoid.project.client.manage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ru.murzoid.project.shared.DBManageException;
import ru.murzoid.project.shared.TablesEnum;

@RemoteServiceRelativePath("dbManage")
public interface DBManage  extends RemoteService {

	List<Map<String, Serializable>> load(TablesEnum table) throws DBManageException;
	String save(TablesEnum table, List<Map<String, Serializable>> list) throws DBManageException;
	String refresh(TablesEnum table) throws DBManageException;

}
