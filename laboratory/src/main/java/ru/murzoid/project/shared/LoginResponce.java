package ru.murzoid.project.shared;

import java.io.Serializable;

public class LoginResponce implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2706462964569993524L;
	private String response;
	private String sessionID;
	private boolean admin=false;
	private boolean testPass=false;
	private boolean success=false;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String toString() {
		return "LoginResponce [response=" + response + ", admin=" + admin
				+ ", success=" + success + "]";
	}
	public boolean isTestPass() {
		return testPass;
	}
	public void setTestPass(boolean testPass) {
		this.testPass = testPass;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
}
