package ru.murzoid.ldap.adam.install.exception;

import javax.naming.NamingException;


public class ADAMManagerException extends NamingException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5063009608499303745L;

	private Throwable ex;
	
	public ADAMManagerException() {
		super();
	}
	
	public ADAMManagerException( String message) {
		super(message);
	}
	public ADAMManagerException(Throwable exception) {
		this.ex=exception;
	}
	public ADAMManagerException( String message, Throwable ex) {
		super(message);
		this.ex=ex;
	}

	public Throwable getEx() {
		return ex;
	}
}
