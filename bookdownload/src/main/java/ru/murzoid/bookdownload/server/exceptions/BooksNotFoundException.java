package ru.murzoid.bookdownload.server.exceptions;

public class BooksNotFoundException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1799499873576946767L;

	public BooksNotFoundException(String message) {
		super(message);
	}
}
