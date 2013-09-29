package ru.murzoid.bookdownload.server.analyze;

import java.io.File;
import java.util.Map;

import javax.ejb.Local;

import ru.murzoid.bookdownload.server.dto.Book;
import ru.murzoid.bookdownload.server.exceptions.FirstAnalyzeIsRunnigException;

@Local
public interface BookAnalyzerInterface{
	
	public void executeAnalyze();
	public Long getIteration();
	public Map<String, Book> getBooks();
	public File getBook(String name, String tmpFolder) throws FirstAnalyzeIsRunnigException;
}