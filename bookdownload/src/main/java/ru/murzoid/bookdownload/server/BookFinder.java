package ru.murzoid.bookdownload.server;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.bookdownload.server.analyze.BookAnalyzerInterface;
import ru.murzoid.bookdownload.server.exceptions.BooksNotFoundException;
import ru.murzoid.bookdownload.server.exceptions.FirstAnalyzeIsRunnigException;
import ru.murzoid.bookdownload.server.util.ZipHelper;

@Stateless
public class BookFinder {


	private static final String REL_APP_PATH_DEFAULT = "/deployments/books.war/";
	
	private static final String BASE_DIR_DAFAULT = "D:\\servers\\jboss711F\\standalone";

	private static final String JBOSS_SERVER_BASE_DIR = "jboss.server.base.dir";

	private static final String BOOKS_NOT_FOUND = "книги не были найдены";

	private static final String FOUND = " была найдена в архивах";

	private static final String NOT_FOUND = " была не найдена в архивах";

	private static final String BOOK = "книга ";

	private static final String COMMA = ",";

	private static final String SLASH = "/";

	private static final String DEFAULT_TEMP_FOLDER_PATH = "D:\\servers\\jboss711F\\standalone\\tmp";

	private static final String JBOSS_SERVER_TEMP_DIR = "jboss.server.temp.dir";
	private static final String FOLDER_BOOKS = "books/";
	public static Logger log = LogManager.getLogger(BookFinder.class);
	
	@EJB
	private BookAnalyzerInterface bookAnalyzer;
	
	public String findBook(String input) throws FirstAnalyzeIsRunnigException, BooksNotFoundException {
		String[] books=input.split(COMMA);
		Set<File> files=new HashSet<File>();
		String tmpFolder=System.getProperty(JBOSS_SERVER_TEMP_DIR,DEFAULT_TEMP_FOLDER_PATH)+SLASH+System.currentTimeMillis()+SLASH;
		File folderFile=new File(tmpFolder);
		folderFile.mkdirs();
		for(String name: books){
			File file=bookAnalyzer.getBook(name, tmpFolder);
			if(file==null){
				log.info(BOOK+name+NOT_FOUND);
			} else {
				files.add(file);
				log.info(BOOK+name+FOUND);
			}
		}
		if(files.isEmpty()){
			throw new BooksNotFoundException(BOOKS_NOT_FOUND);
		} else {
			new BookDelete(new File(tmpFolder));
		}
		return archiveFiles(files);
	}

	private String archiveFiles(Set<File> files) {
		String archiveName=FOLDER_BOOKS+System.currentTimeMillis()+".zip";
		String tmpPath=System.getProperty(JBOSS_SERVER_BASE_DIR,BASE_DIR_DAFAULT)+REL_APP_PATH_DEFAULT+archiveName;
		ZipHelper zipHlp=new ZipHelper();
		zipHlp.createZip(files, tmpPath);
		new BookDelete(new File(tmpPath));
		return archiveName;
	}
}
