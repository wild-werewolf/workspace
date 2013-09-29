package ru.murzoid.project.client;

import ru.murzoid.project.shared.LoginResponce;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LogonManager {

	private static LogonManager logon;
	private final LogonServiceAsync logonService = GWT
			.create(LogonService.class);
	
	public static LogonManager getInstance(){
		if(logon==null){
			logon=new LogonManager();
		}
		return logon;
	}

	public void login(String passToServer, String textToServer, AsyncCallback<LoginResponce> asyncCallback){
		logonService.login(textToServer, passToServer, asyncCallback);
	}

	public void relogin(String sessionToServer, String nameToServer, AsyncCallback<LoginResponce> asyncCallback){
		logonService.relogin(nameToServer, sessionToServer, asyncCallback);
	}
	
	public void changePassword(String newPass, String input, String pass, AsyncCallback<String> callback){
		logonService.changePass(input, pass, newPass, callback);
	}
	
	public void logout(String name, AsyncCallback<String> callback){
		logonService.logout(name, callback);
	}
}
