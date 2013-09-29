package ru.murzoid.bookdownload.server.analyze;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.bookdownload.server.dto.Book;
import ru.murzoid.bookdownload.server.exceptions.FirstAnalyzeIsRunnigException;
import ru.murzoid.bookdownload.server.util.LookupAPI;
import ru.murzoid.bookdownload.server.util.LookupConstants;
import ru.murzoid.bookdownload.server.util.ZipHelper;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Lock(LockType.READ)
public class BookAnalyzer implements BookAnalyzerInterface{


	private static final String ERROR_OCCURED_WHILE_WAS_EXTRACTING_FILE = "error occured while was extracting file";

	private static final String FILE_TO_COPY = "file to copy ";

	private static final String PROBLEM_WITH_I_O_ARHIVE = "problem with I/O arhive ";

	private static final String PROBLEM_WITH_ARHIVE = "problem with arhive ";
	
	private static Logger log = LogManager.getLogger(BookAnalyzer.class);

	private List<File> rootDirs;
	private Map<String, Book> books;
	private Long iteration;

	private BookAnalyzer() {
		iteration = 0l;
		String fb2Path = System.getProperty("books.home.fb2");
		String usrPath = System.getProperty("books.home.usr");
		rootDirs = new ArrayList<File>();
		if(fb2Path!=null && !fb2Path.isEmpty()){
			rootDirs.add(new File(fb2Path));
		}
		if(usrPath!=null && !usrPath.isEmpty()){
			rootDirs.add(new File(usrPath));
		}
		books = new HashMap<String, Book>();
	}

	@PostConstruct
	public void start(){
		System.out.println("class BookAnalyzer started");
	}
	
	@Asynchronous
	@Lock(LockType.READ)
	public void executeAnalyze() {
		log.info("Start " + iteration + " iteration analyze books");
		HashMap<String, Book> map=new HashMap<String, Book>();
	    Vector<Future<HashMap<String, Book>>> vector = new Vector<Future<HashMap<String, Book>>>();
		for (File rootDir : rootDirs) {
			for (File arhive : rootDir.listFiles()) {
				if (arhive.isFile()
						&& (arhive.getName().endsWith(".zip") && !arhive
								.getName().endsWith(".ZIP"))) {
					ArchiveAnalyzer analyzer=(ArchiveAnalyzer) LookupAPI.lookup(LookupConstants.ARCHIVE_ANALYZER_JNDI_NAME);
					vector.add(analyzer.analyzeArchive(arhive));
				}
			}
		}
		for(Future<HashMap<String, Book>> futureRes:vector){
			try {
				map.putAll(futureRes.get());
			} catch (InterruptedException e) {
				log.error("problem with get map from archive analyzer", e);
			} catch (ExecutionException e) {
				log.error("problem with get map from archive analyzer", e);
			}
		}
		updateMap(map);
		log.info("End " + iteration + " iteration analyze books");
		iteration++;
	}

	private void updateMap(HashMap<String, Book> map) {
		log.info("added "+map.size()+" books to map after archive analyzing");
		books.clear();
		books.putAll(map);
	}

	public Long getIteration() {
		return iteration;
	}

	public Map<String, Book> getBooks() {
		return books;
	}

	@Override
	public File getBook(String name, String tmpFolder) throws FirstAnalyzeIsRunnigException{
		if(getIteration()<=0){
			throw new FirstAnalyzeIsRunnigException("Анализ архивов не завершен");
		}
		File file = null;
		Map<String, Book> map=getBooks();
		if(map.containsKey(name)){
			Book book=map.get(name);
			String fileName=book.getArchivePath();
			file = extractBook(book, tmpFolder, fileName);
		}
		return file;
	}

	private File extractBook(Book book, String tmpPath, String fileName) {
		ZipHelper zipHlp=new ZipHelper();
		ZipFile zip = null;
		try {
			zip = new ZipFile(book.getArchive().getFile());
		} catch (ZipException e1) {
			log.error(PROBLEM_WITH_ARHIVE, e1);
		} catch (IOException e1) {
			log.error(PROBLEM_WITH_I_O_ARHIVE, e1);
		}
		File file=new File(tmpPath+fileName);
		log.debug(FILE_TO_COPY+file.getAbsolutePath());
		ZipEntry entry=new ZipEntry(book.getArchivePath());
		try {
			zipHlp.write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			log.error(ERROR_OCCURED_WHILE_WAS_EXTRACTING_FILE, e);
		} catch (IOException e) {
			log.error(ERROR_OCCURED_WHILE_WAS_EXTRACTING_FILE, e);
		}
		return file;
	}
}