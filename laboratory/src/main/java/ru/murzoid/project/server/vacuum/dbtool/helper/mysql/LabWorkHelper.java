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
import ru.murzoid.project.server.vacuum.dbtool.tables.LabWorkTable;

public class LabWorkHelper implements TablesHelper{

	public static Logger log = LogManager.getLogger(LabWorkHelper.class);
	private List<LabWorkTable> list = new ArrayList<LabWorkTable>();

	public void load(Connection connection){
		log.info("load Lab Work");
		String query = "SELECT id, idUser, idLabVariant, Apol, Bpol, hos, rezultTest, V02, V04, V06, V08, V10, V12, V14, V16, V18, V20, V22, V24, V26, V28 FROM labwork ORDER BY id";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				LabWorkTable labWok=new LabWorkTable();
				labWok.setId(rs.getLong("id"));
				labWok.setIdUser(rs.getLong("idUser"));
				labWok.setIdLabVar(rs.getLong("idLabVariant"));	
				labWok.setConstA(rs.getDouble("Apol"));
				labWok.setConstB(rs.getDouble("Bpol"));
				labWok.setHos(rs.getDouble("hos"));
				labWok.setTestPass(rs.getBoolean("rezultTest"));
				labWok.setTime1(rs.getInt("V02"));
				labWok.setTime2(rs.getInt("V04"));
				labWok.setTime3(rs.getInt("V06"));
				labWok.setTime4(rs.getInt("V08"));
				labWok.setTime5(rs.getInt("V10"));
				labWok.setTime6(rs.getInt("V12"));
				labWok.setTime7(rs.getInt("V14"));
				labWok.setTime8(rs.getInt("V16"));
				labWok.setTime9(rs.getInt("V18"));
				labWok.setTime10(rs.getInt("V20"));
				labWok.setTime11(rs.getInt("V22"));
				labWok.setTime12(rs.getInt("V24"));
				labWok.setTime13(rs.getInt("V26"));
				labWok.setTime14(rs.getInt("V28"));
				list.add(labWok);	
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		log.info(list);
		log.info("load Lab Work end");
	}

	@SuppressWarnings("unchecked")
	public List<LabWorkTable> getList() {
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
		String query = "TRUNCATE labwork";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into labwork (id, idUser, idLabVariant, Apol, Bpol, hos, rezultTest, V02, V04, V06, V08, V10, V12, V14, V16, V18, V20, V22, V24, V26, V28) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for(Map<String, Serializable> map:list){
				insert.append("(");
				insert.append(map.get("id"));
				insert.append(",");
				insert.append(map.get("idUser"));
				insert.append(",");
				insert.append(map.get("idLabVariant"));	
				insert.append(",");
				insert.append(map.get("Apol"));
				insert.append(",");
				insert.append(map.get("Bpol"));
				insert.append(",");
				insert.append(map.get("hos"));
				insert.append(",");
				insert.append(map.get("rezultTest"));
				insert.append(",");
				insert.append(map.get("V02"));
				insert.append(",");
				insert.append(map.get("V04"));
				insert.append(",");
				insert.append(map.get("V06"));
				insert.append(",");
				insert.append(map.get("V08"));
				insert.append(",");
				insert.append(map.get("V10"));
				insert.append(",");
				insert.append(map.get("V12"));
				insert.append(",");
				insert.append(map.get("V14"));
				insert.append(",");
				insert.append(map.get("V16"));
				insert.append(",");
				insert.append(map.get("V18"));
				insert.append(",");
				insert.append(map.get("V20"));
				insert.append(",");
				insert.append(map.get("V22"));
				insert.append(",");
				insert.append(map.get("V24"));
				insert.append(",");
				insert.append(map.get("V26"));
				insert.append(",");
				insert.append(map.get("V28"));
				insert.append("),");
			}
			insert.setLength(insert.length()-1);
			System.out.println(insert);
			st.execute(insert.toString());
			st.close();
			list.clear();
			load(connection);
			log.info("save grid data end");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void save(Connection connection, List<LabWorkTable> list) {
		log.info("save grid data start");
		String query = "TRUNCATE labwork";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into labwork (id, idUser, idLabVariant, Apol, Bpol, hos, rezultTest, V02, V04, V06, V08, V10, V12, V14, V16, V18, V20, V22, V24, V26, V28) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for(InterfaceTable table:list){
				LabWorkTable labWork=(LabWorkTable) table;
				insert.append("(");
				insert.append(labWork.getId());
				insert.append(",");
				insert.append(labWork.getIdUser());
				insert.append(",");
				insert.append(labWork.getIdLabVar());	
				insert.append(",");
				insert.append(labWork.getConstA());
				insert.append(",");
				insert.append(labWork.getConstB());
				insert.append(",");
				insert.append(labWork.getHos());
				insert.append(",");
				insert.append(labWork.isTestPass());
				insert.append(",");
				insert.append(labWork.getTime1());
				insert.append(",");
				insert.append(labWork.getTime2());
				insert.append(",");
				insert.append(labWork.getTime3());
				insert.append(",");
				insert.append(labWork.getTime4());
				insert.append(",");
				insert.append(labWork.getTime5());
				insert.append(",");
				insert.append(labWork.getTime6());
				insert.append(",");
				insert.append(labWork.getTime7());
				insert.append(",");
				insert.append(labWork.getTime8());
				insert.append(",");
				insert.append(labWork.getTime9());
				insert.append(",");
				insert.append(labWork.getTime10());
				insert.append(",");
				insert.append(labWork.getTime11());
				insert.append(",");
				insert.append(labWork.getTime12());
				insert.append(",");
				insert.append(labWork.getTime13());
				insert.append(",");
				insert.append(labWork.getTime14());
				insert.append("),");
			}
			insert.setLength(insert.length()-1);
			log.info(insert);
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

	public void update(Connection connection, LabWorkTable labWork) {
		if(labWork==null || labWork.getId()<0){
			return;
		}
		log.info("update grid data start");
		StringBuffer insert = new StringBuffer();
		insert.append("update labwork set ");
		try {
			Statement st = connection.createStatement();
			insert.append("idUser=");
			insert.append(labWork.getIdUser());
			insert.append(", idLabVariant=");
			insert.append(labWork.getIdLabVar());
			insert.append(", Apol=");
			insert.append(labWork.getConstA());
			insert.append(", Bpol=");
			insert.append(labWork.getConstB());
			insert.append(", hos=");
			insert.append(labWork.getHos());
			insert.append(", rezultTest=");
			insert.append(labWork.isTestPass());
			insert.append(", V02=");
			insert.append(labWork.getTime1());
			insert.append(", V04=");
			insert.append(labWork.getTime2());
			insert.append(", V06=");
			insert.append(labWork.getTime3());
			insert.append(", V08=");
			insert.append(labWork.getTime4());
			insert.append(", V10=");
			insert.append(labWork.getTime5());
			insert.append(", V12=");
			insert.append(labWork.getTime6());
			insert.append(", V14=");
			insert.append(labWork.getTime7());
			insert.append(", V16=");
			insert.append(labWork.getTime8());
			insert.append(", V18=");
			insert.append(labWork.getTime9());
			insert.append(", V20=");
			insert.append(labWork.getTime10());
			insert.append(", V22=");
			insert.append(labWork.getTime11());
			insert.append(", V24=");
			insert.append(labWork.getTime12());
			insert.append(", V26=");
			insert.append(labWork.getTime13());
			insert.append(", V28=");
			insert.append(labWork.getTime14());
			insert.append(" WHERE id=");
			insert.append(labWork.getId());
			log.info(insert);
			st.executeUpdate(insert.toString());
			st.close();
			list.clear();
			load(connection);
			log.info("update grid data end");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
}
