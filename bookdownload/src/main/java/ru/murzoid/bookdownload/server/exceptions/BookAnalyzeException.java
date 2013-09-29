package ru.murzoid.bookdownload.server.exceptions;

import java.io.IOException;

public class BookAnalyzeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BookAnalyzeException(String string, IOException e) {
		super(string, e);
	}
	
	public BookAnalyzeException(IOException e) {
		super(e);
	}
	
	public BookAnalyzeException(String string) {
		super(string);
	}

}
