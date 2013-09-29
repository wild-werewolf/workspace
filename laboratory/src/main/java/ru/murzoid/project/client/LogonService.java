package ru.murzoid.project.client;

import ru.murzoid.project.shared.LoginException;
import ru.murzoid.project.shared.LoginResponce;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("login")
public interface LogonService extends RemoteService {
	LoginResponce login(String name, String pass) throws LoginException;
	LoginResponce relogin(String name, String session) throws LoginException;
	String changePass(String name, String pass, String newPass) throws LoginException;
	String logout(String name) throws LoginException;
}