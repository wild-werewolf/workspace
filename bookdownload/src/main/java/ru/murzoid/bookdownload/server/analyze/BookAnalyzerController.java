package ru.murzoid.bookdownload.server.analyze;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.ObjectName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.bookdownload.server.util.LookupConstants;
import ru.murzoid.bookdownload.server.util.MBeanRegisterUtil;

@Startup
@Singleton
public class BookAnalyzerController implements BookAnalyzerControllerMBean {

	private static final String START = "Start ";

	private static final String ITERATION_ANALYZE_BOOKS = " iteration analyze books";

	private static Logger log = LogManager.getLogger(BookAnalyzerController.class);

	@EJB
	private BookAnalyzerInterface bookAnalyzer;

	private BookAnalyzerController() {
	}

	private ObjectName objectName = null;

	@PostConstruct
	public void registerInJMX() throws Exception {
		objectName = new ObjectName(LookupConstants.BOOK_ANALYZER_OBJECT_NAME);
		(new MBeanRegisterUtil()).registerMBean(this, objectName);
		executeAnalyze();
	}

	@PreDestroy
	public void unregisterFromJMX() throws Exception {
		(new MBeanRegisterUtil()).unregisterMBean(this, objectName);
	}
	
	public void executeAnalyze() {
		log.info(START + bookAnalyzer.getIteration() + ITERATION_ANALYZE_BOOKS);
		bookAnalyzer.executeAnalyze();
	}

	@Schedule(dayOfWeek = "1")
	public void doAnalyze() {
		executeAnalyze();
	}

	@Override
	@Lock(LockType.READ)
	public String getJndiName() {
		return LookupConstants.BOOK_ANALYZER_SERVICE_JNDI_NAME;
	}

	@Override
	@Lock(LockType.READ)
	public String getObjectName() {
		return LookupConstants.BOOK_ANALYZER_OBJECT_NAME;
	}
}