package ru.murzoid.bookdownload.server.analyze;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.bookdownload.server.dto.Archive;
import ru.murzoid.bookdownload.server.dto.Book;
import ru.murzoid.bookdownload.server.exceptions.BookAnalyzeException;
import ru.murzoid.bookdownload.server.util.ZipHelper;

@Stateless
public class ArchiveAnalyzer {

	private static Logger log = LogManager.getLogger(ArchiveAnalyzer.class);
	
	@Asynchronous
	public Future<HashMap<String, Book>> analyzeArchive(File arhive){
		ZipHelper zh = new ZipHelper();
		HashMap<String, Book> map=new HashMap<String, Book>();
		try {
			Archive archive = new Archive(arhive);
			for (String bookArchivePath : zh.analyze(arhive)) {
				Book book = new Book(archive, bookArchivePath);
				map.put(book.getName(), book);
			}
		} catch (BookAnalyzeException e) {
			log.error(e.getMessage(), e);
		}
		return new AsyncResult<HashMap<String,Book>>(map);
	}
}
