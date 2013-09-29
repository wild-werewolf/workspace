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
import ru.murzoid.project.server.vacuum.dbtool.tables.QuestionsTable;

public class QuestionsHelper implements TablesHelper{

	public static Logger log = LogManager.getLogger(QuestionsHelper.class);
	private List<QuestionsTable> list = new ArrayList<QuestionsTable>();

	public void load(Connection connection){
		String query = "SELECT id, gruppa, question, variants, variants_true, image FROM questions ORDER BY id";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				QuestionsTable labWok=new QuestionsTable();
				labWok.setId(rs.getLong("id"));
				labWok.setGruppa(rs.getString("gruppa"));
				labWok.setQuestion(rs.getString("question"));
				String[] str=rs.getString("variants").split(",");
				ArrayList<Long> var=new ArrayList<Long>();
				for(String tmp: str){
					if(tmp!=null && !tmp.trim().equals("")){
						Long i=Long.parseLong(tmp);
						var.add(i);
					}
				}
				labWok.setAnswer(var);
				String[] strT=rs.getString("variants_true").split(",");
				ArrayList<Long> varT=new ArrayList<Long>();
				for(String tmp: strT){
					if(tmp!=null && !tmp.trim().equals("")){
						Long i=Long.parseLong(tmp);
						varT.add(i);
					}
				}
				labWok.setTrueAnswer(varT);
				if(rs.getString("image")==null){
					labWok.setImage("");
				} else {
					labWok.setImage(rs.getString("image"));
				}
				list.add(labWok);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<QuestionsTable> getList() {
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

	@SuppressWarnings("unchecked")
	@Override
	public void save(List<Map<String, Serializable>> list, Connection connection) {
		log.info("save grid data start");
		String query = "TRUNCATE questions";
		StringBuffer insert = new StringBuffer();
		insert.append("insert into questions (id, gruppa, question, variants, variants_true, image) values");
		try {
			Statement st = connection.createStatement();
			st.execute(query);
			for(Map<String, Serializable> map:list){
				insert.append("(");
				insert.append(map.get("id"));
				insert.append(",'");
				insert.append(map.get("gruppa"));
				insert.append("','");
				insert.append(map.get("question"));
				insert.append("','");
				for(Long var: (List<Long>)map.get("variants")){
					insert.append(var);
					insert.append(",");
				}
				if(((List<Long>)map.get("variants")).size()>0){
					insert.setLength(insert.length()-1);
				}
				insert.append("','");
				for(Long var: (List<Long>)map.get("variants_true")){
					insert.append(var);
					insert.append(",");
				}
				if(((List<Long>)map.get("variants_true")).size()>0){
					insert.setLength(insert.length()-1);
				}
				insert.append("','");
				insert.append(map.get("image"));
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
