package ru.murzoid.project.server.vacuum.dbtool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.AnswerHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.HaracOsadokHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.HaracPeregHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.LabVariantHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.LabWorkHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.QuestionsHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.TablesHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.UsersHelper;
import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;
import ru.murzoid.project.shared.TablesEnum;

public class LibHelper {

	private static Connection conn;
	public static Logger log = LogManager.getLogger(LibHelper.class);
	public static HashMap<TablesEnum, TablesHelper> helpers=new HashMap<TablesEnum, TablesHelper>();
	private static Properties props;
	private static Properties dbprops;
	private static String homePath;

	static{
		helpers.put(TablesEnum.Answer, new AnswerHelper());
		helpers.put(TablesEnum.HaracOsadok, new HaracOsadokHelper());
		helpers.put(TablesEnum.HaracPeregorodka, new HaracPeregHelper());
		helpers.put(TablesEnum.LabVariant, new LabVariantHelper());
		helpers.put(TablesEnum.LabWork, new LabWorkHelper());
		helpers.put(TablesEnum.Questions, new QuestionsHelper());
		helpers.put(TablesEnum.Users, new UsersHelper());
	}
	public static void libInitial() {
		log.info("initial library...");
		props = new Properties();
		try {
			props.load(new FileReader(new File(homePath
					+ "/config/dbconn.prop")));
		} catch (InvalidPropertiesFormatException e1) {
			log.error("invalid properties format for DB tool", e1);
			return;
		} catch (FileNotFoundException e1) {
			log.error("properties file not found for DB tool", e1);
			return;
		} catch (IOException e1) {
			log.error("proplem with load properties for DB tool", e1);
			return;
		}
		try {
			Class.forName(props.getProperty("driver")).newInstance();
			log.info(props.getProperty("driver"));
		} catch (InstantiationException e) {
			log.error("problem with initial driver for dbtool", e);
			return;
		} catch (IllegalAccessException e) {
			log.error("problem with access to driver", e);
			return;
		} catch (ClassNotFoundException e) {
			log.error("driver class not found ", e);
			return;
		}
		dbprops = new Properties();
		dbprops.put("user", props.getProperty("user"));
		log.info(props.getProperty("user"));
		dbprops.put("password", props.getProperty("password"));
		log.info(props.getProperty("password"));
		dbprops.put("useUnicode", props.getProperty("useUnicode"));
		log.info(props.getProperty("useUnicode"));
		dbprops.put("characterEncoding", props.getProperty("characterEncoding"));
		log.info(props.getProperty("characterEncoding"));
		log.info(dbprops);
		try {
			log.info(props.getProperty("url"));
			conn = DriverManager.getConnection(props.getProperty("url"), dbprops);
		} catch (SQLException e) {
			log.error("problem with get connection", e);
			return;
		}
		for(TablesEnum table: helpers.keySet()){
			helpers.get(table).load(conn);
		}
		log.info("end initial library successful");
	}
	
	public static List<Map<String, Serializable>> getGridData(TablesEnum tableEnum){
		log.info("load grid data from LibHelper");
		TablesHelper helper=helpers.get(tableEnum);
		return helper.getGridData();
	}

	public static Connection getConn() {
		return conn;
	}
	
	public static Connection getNewConnection() {
		log.info("start get new connection");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(props.getProperty("url"), dbprops);
		} catch (SQLException e) {
			log.error("problem with get new connection", e);
			return connection;
		}
		log.info("end get new connection");
		return connection;
	}
	

//	public static List<Map<String, Serializable>> load(TablesEnum table) {
//		log.info("load grid data from LibHelper");
//		return helpers.get(table).getGridData();
//	}
	
	public static void save(TablesEnum table, List<Map<String, Serializable>> list){
		log.info("save grid data in LibHelper");
		helpers.get(table).save(list, conn);
	}

	public static void refresh(TablesEnum table) {
		helpers.get(table).refresh();		
	}
	
	public static<T extends InterfaceTable> List<T> getList(TablesEnum table){
		log.info("lib helper get List data");
		log.info(helpers.get(table).getList());
		return helpers.get(table).getList();
	}

	public static String getHomePath() {
		return homePath;
	}

	public static void setHomePath(String homePath) {
		LibHelper.homePath = homePath;
	}
	
	
	public static void main(String [] args) {
		dbprops = new Properties();
		dbprops.put("user",  "root");
		dbprops.put("password", "raport");
		dbprops.put("useUnicode", "true");
		dbprops.put("characterEncoding", "UTF8");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", dbprops);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
