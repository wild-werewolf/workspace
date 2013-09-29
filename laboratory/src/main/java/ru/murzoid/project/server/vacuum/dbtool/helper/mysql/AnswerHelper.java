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
import ru.murzoid.project.server.vacuum.dbtool.tables.AnswerTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;

public class AnswerHelper implements TablesHelper{

	public static Logger log = LogManager.getLogger(AnswerHelper.class);
	private List<AnswerTable> list = new ArrayList<AnswerTable>();

	public void load(Connection connection){
		String query = "SELECT id, variant FROM answer ORDER BY id";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				AnswerTable labWok=new AnswerTable();
				labWok.setId(rs.getLong("id"));
				labWok.setAnswer(rs.getString("variant"));
				list.add(labWok);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AnswerTable> getList() {
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
		String query = "TRUNCATE answer";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into answer (id, variant) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for(Map<String, Serializable> map:list){
				insert.append("(");
				insert.append(map.get("id"));
				insert.append(",'");
				insert.append(map.get("variant"));
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
