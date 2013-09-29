package ru.murzoid.project.client;

import ru.murzoid.project.shared.LoginException;
import ru.murzoid.project.shared.LoginResponce;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface LogonServiceAsync {
	void login(String name, String pass, AsyncCallback<LoginResponce> callback)
		throws LoginException;
	void relogin(String name, String session, AsyncCallback<LoginResponce> callback)
		throws LoginException;

	void changePass(String input, String pass, String newPass, AsyncCallback<String> callback)
			throws LoginException;

	void logout(String name, AsyncCallback<String> callback) throws LoginException;
}
