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
import ru.murzoid.project.server.vacuum.dbtool.tables.UsersTable;

public class UsersHelper implements TablesHelper{

	public static Logger log = LogManager.getLogger(UsersHelper.class);
	private List<UsersTable> list = new ArrayList<UsersTable>();

	public void load(Connection connection){
		String query = "SELECT id, name, login, password, gruppa, permission FROM users ORDER BY id";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				UsersTable labWok=new UsersTable();
				labWok.setId(rs.getLong("id"));
				labWok.setName(rs.getString("name"));
				labWok.setLogin(rs.getString("login"));
				labWok.setPassword(rs.getString("password"));
				labWok.setGruppa(rs.getString("gruppa"));
				labWok.setPermission(rs.getString("permission"));
				list.add(labWok);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<UsersTable> getList() {
		log.info("user helper get List data");
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
		String query = "TRUNCATE users";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into users (id, name, login, password, gruppa, permission) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for(Map<String, Serializable> map:list){
				insert.append("(");
				insert.append(map.get("id"));
				insert.append(",'");
				insert.append(map.get("name"));
				insert.append("','");
				insert.append(map.get("login"));
				insert.append("','");
				insert.append(map.get("password"));
				insert.append("','");
				insert.append(map.get("gruppa"));
				insert.append("','");
				insert.append(map.get("permission"));
				insert.append("'),");
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
