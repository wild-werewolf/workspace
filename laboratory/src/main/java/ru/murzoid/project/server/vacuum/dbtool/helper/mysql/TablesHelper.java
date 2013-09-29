package ru.murzoid.project.server.vacuum.dbtool.helper.mysql;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;

public interface TablesHelper {
	public void load(Connection connection);

	public List<Map<String, Serializable>> getGridData();

	public void save(List<Map<String, Serializable>> list, Connection connection);

	public void refresh();
	
	public<T extends InterfaceTable> List<T> getList();
}
