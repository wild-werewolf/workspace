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
import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.LabVariantTable;

public class LabVariantHelper implements TablesHelper {

	public static Logger log = LogManager.getLogger(LabVariantHelper.class);
	private List<LabVariantTable> list = new ArrayList<LabVariantTable>();

	public void load(Connection connection) {
		String query = "SELECT id, name, GC, XC, WOC, temper, deltap, id_harac_osadok, id_harac_pereg FROM labvariant ORDER BY id";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			list.clear();
			while (rs.next()) {
				LabVariantTable labVar = new LabVariantTable();
				labVar.setId(rs.getLong("id"));
				labVar.setName(rs.getString("name"));
				labVar.setGc(rs.getDouble("GC"));
				labVar.setXc(rs.getDouble("XC"));
				labVar.setWoc(rs.getDouble("WOC"));
				labVar.setTemper(rs.getInt("temper"));
				labVar.setDeltap(rs.getDouble("deltap"));
				labVar.setId_harac_osad(rs.getLong("id_harac_osadok"));
				labVar.setId_harac_per(rs.getLong("id_harac_pereg"));
				list.add(labVar);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<LabVariantTable> getList() {
		return list;
	}

	@Override
	public List<Map<String, Serializable>> getGridData() {
		List<Map<String, Serializable>> gridData = new ArrayList<Map<String, Serializable>>();
		for (InterfaceTable table : list) {
			gridData.add(table.getGridData());
		}
		return gridData;
	}

	@Override
	public void save(List<Map<String, Serializable>> list, Connection connection) {
		log.info("save grid data start");
		String query = "TRUNCATE labvariant";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into labvariant (id, name, GC, XC, WOC, temper, deltap, id_harac_osadok, id_harac_pereg) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for (Map<String, Serializable> map : list) {
				insert.append("(");
				insert.append(map.get("id"));
				insert.append(",'");
				insert.append(map.get("name"));
				insert.append("',");
				insert.append(map.get("GC"));
				insert.append(",");
				insert.append(map.get("XC"));
				insert.append(",");
				insert.append(map.get("WOC"));
				insert.append(",");
				insert.append(map.get("temper"));
				insert.append(",");
				insert.append(map.get("deltap"));
				insert.append(",");
				insert.append(map.get("id_harac_osadok"));
				insert.append(",");
				insert.append(map.get("id_harac_pereg"));
				insert.append("),");
			}
			insert.setLength(insert.length() - 1);
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
