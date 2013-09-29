package ru.murzoid.project.server.vacuum.dbtool.helper.mysql;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.project.server.vacuum.dbtool.LibHelper;
import ru.murzoid.project.server.vacuum.dbtool.tables.HaracPeregTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;

public class HaracPeregHelper implements TablesHelper{

	public static Logger log = LogManager.getLogger(HaracPeregHelper.class);
	private List<HaracPeregTable> list = new ArrayList<HaracPeregTable>();

	public void load(Connection connection){
		String query = "SELECT id, name, soprot FROM harac_pereg ORDER BY id";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				HaracPeregTable labWok=new HaracPeregTable();
				labWok.setId(rs.getLong("id"));
				labWok.setName(rs.getString("name"));
				labWok.setSoprot(rs.getDouble("soprot"));
				list.add(labWok);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<HaracPeregTable> getList() {
		return list;
	}

	@Override
	public List<Map<String, Serializable>> getGridData() {
		List<Map<String, Serializable>> gridData=new ArrayList<Map<String,Serializable>>();
		for(InterfaceTable table: list){
			gridData.add(table.getGridData());
		}
		return gridData;
	}

	@Override
	public void save(List<Map<String, Serializable>> list, Connection connection) {
		log.info("save grid data start");
		String query = "TRUNCATE harac_pereg";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into harac_pereg (id, name, soprot) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for(Map<String, Serializable> map:list){
				insert.append("(");
				insert.append(map.get("id"));
				insert.append(",'");
				insert.append(map.get("name"));
				insert.append("',");
				insert.append(map.get("soprot"));
				insert.append("),");
			}
			insert.setLength(insert.length()-1);
			st.execute(insert.toString());
			st.close();
			list.clear();
			load(connection);
			log.info("save grid data end");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@Override
	public void refresh() {
		list.clear();
		load(LibHelper.getConn());
	}
}
