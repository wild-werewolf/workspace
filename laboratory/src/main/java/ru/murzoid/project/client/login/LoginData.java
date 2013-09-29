package ru.murzoid.project.client.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.Cookies;

public class LoginData implements LoginNotifier {

	private static final String cookieName = "Web Labaratory Murzoid";
	private static final String cookieSessionName = "Web Labaratory Murzoid SessionID";
	private static final int COOKIE_TIMEOUT = 1000 * 60 * 60 * 24 * 14;
	private boolean success = false;
	private String userName = "";
	private String session = "";
	private boolean admin;
	private boolean testPass=false;
	private List<LoginListener> listeners = new ArrayList<LoginListener>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
		for (LoginListener loginListener : listeners) {
			loginListener.onChange();
		}
	}

	public void addLoginListener(final LoginListener listener) {
		this.listeners.add(listener);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void getCookie() {
		if (Cookies.isCookieEnabled()){
			userName = Cookies.getCookie(cookieName);
			session = Cookies.getCookie(cookieSessionName);
		}
	}

	public void setCookie() {
        Date expires = new Date((new Date()).getTime() + COOKIE_TIMEOUT);
		if (Cookies.isCookieEnabled()){
			Cookies.setCookie(cookieName, userName,expires);
			Cookies.setCookie(cookieSessionName, session, expires);
		}
	}

	public void deleteCookie() {
		if (Cookies.isCookieEnabled()){
			Cookies.removeCookie(cookieSessionName);
		}
	}

	public boolean isTestPass() {
		return testPass;
	}

	public void setTestPass(boolean testPass) {
		this.testPass = testPass;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
}
