package ru.murzoid.bookdownload.server.test;

import junit.framework.TestCase;

import org.apache.log4j.xml.DOMConfigurator;

import ru.murzoid.bookdownload.server.util.LookupAPI;
import ru.murzoid.bookdownload.server.util.LookupConstants;

public class TestBD extends TestCase {

	public void testAnalyze(){
        DOMConfigurator.configure("src/test/resources/log4j.xml");
//        LookupAPI.lookup(LookupConstants.BOOK_ANALYZER_JNDI_NAME);
        try {
			Thread.sleep(1000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block			e.printStackTrace();
		}
	}
}
