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
import ru.murzoid.project.server.vacuum.dbtool.*;
import ru.murzoid.project.server.vacuum.dbtool.tables.HaracOsadokTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;

public class HaracOsadokHelper implements TablesHelper{

	public static Logger log = LogManager.getLogger(HaracOsadokHelper.class);
	private List<HaracOsadokTable> list = new ArrayList<HaracOsadokTable>();

	public void load(Connection connection){
		log.info("start load HaracOsadok");
		String query = "SELECT id, name, udelsopr, plottverfaz FROM harac_osadok ORDER BY id";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				HaracOsadokTable labWok=new HaracOsadokTable();
				labWok.setId(rs.getLong("id"));
				labWok.setName(rs.getString("name"));
				labWok.setUdelsopr(rs.getDouble("udelsopr"));
				labWok.setPloTverFaz(rs.getInt("plottverfaz"));
				list.add(labWok);
			}
			st.close();
			log.info("end load HaracOsadok");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<HaracOsadokTable> getList() {
		return list;
	}

	@Override
	public List<Map<String, Serializable>> getGridData() {
		log.info("get Grid Data for Harac Osadok");
		List<Map<String, Serializable>> gridData=new ArrayList<Map<String,Serializable>>();
		for(InterfaceTable table: list){
			gridData.add(table.getGridData());
		}
		return gridData;
	}

	@Override
	public void save(List<Map<String, Serializable>> list, Connection connection) {
		log.info("save grid data start");
		String query = "TRUNCATE harac_osadok";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into harac_osadok (id, name, udelsopr, plottverfaz) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for(Map<String, Serializable> map:list){
				insert.append("(");
				insert.append(map.get("id"));
				insert.append(",'");
				insert.append(map.get("name"));
				insert.append("',");
				insert.append(map.get("udelsopr"));
				insert.append(",");
				insert.append(map.get("plottverfaz"));
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
