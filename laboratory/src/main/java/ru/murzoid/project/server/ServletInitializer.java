 package ru.murzoid.project.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import ru.murzoid.project.server.vacuum.dbtool.LibHelper;

public class ServletInitializer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6479412210585330689L;

	public static Logger log = LogManager.getLogger(ServletInitializer.class);

	private static String homePath;

	public void init() throws ServletException {
		// / Automatically java script can run here
		System.out.println("************");
		System.out.println("*** Web Labaratory Initialized successfully ***..");
		System.out.println("***********");
		String confPath=System.getProperty("weblabaratory.home");
		try {
			homePath=new File(new File(confPath).getCanonicalPath()).getAbsolutePath();
		} catch (IOException e) {
			log.error("proplem with get home folder ", e);
		}
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(confPath+"/configs")));
		} catch (InvalidPropertiesFormatException e1) {
			log.error("invalid properties format for Server Initial", e1);
			return;
		} catch (FileNotFoundException e1) {
			log.error("properties file not found for Server Initial", e1);
			return;
		} catch (IOException e1) {
			log.error("proplem with load properties for Server Initial", e1);
			return;
		}
		String logFile=homePath+File.separator+"log4j.xml";
		System.out.println("logFile="+logFile);
		DOMConfigurator.configure(logFile);
		log.info("************");
		log.info("*** Web Labaratory Initialized successfully***..");
		log.info("***********");
		LibHelper.setHomePath(homePath);
		LibHelper.libInitial();
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public static String getHomePath() {
		return homePath;
	}
}