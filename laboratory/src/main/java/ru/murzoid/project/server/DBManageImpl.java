package ru.murzoid.project.server;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.project.client.manage.DBManage;
import ru.murzoid.project.server.vacuum.dbtool.LibHelper;
import ru.murzoid.project.shared.DBManageException;
import ru.murzoid.project.shared.TablesEnum;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DBManageImpl extends RemoteServiceServlet implements
		DBManage {

	public static Logger log = LogManager.getLogger(DBManageImpl.class);
	public List<Map<String, Serializable>> load(TablesEnum table) throws DBManageException {
		try{
			log.info("load grid data");
			return LibHelper.getGridData(table);
		} catch (Exception e){
			log.error("error: ", e);
			throw new DBManageException(e.getMessage());
		}
	}
	
	public String save(TablesEnum table, List<Map<String, Serializable>> list) throws DBManageException {
		try{
			log.info("save grid data");
			LibHelper.save(table, list);
		return "";
		} catch (Exception e){
			log.error("error: ", e);
			throw new DBManageException(e.getMessage());
		}
	}
	
	public String refresh(TablesEnum table) throws DBManageException {
		try{
			LibHelper.refresh(table);
			return "";
		} catch (Exception e){
			log.error("error: ", e);
			throw new DBManageException(e.getMessage());
		}
	}
}
