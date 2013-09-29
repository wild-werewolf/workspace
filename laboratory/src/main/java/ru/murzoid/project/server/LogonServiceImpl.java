package ru.murzoid.project.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.project.client.LogonService;
import ru.murzoid.project.server.vacuum.dbtool.LibHelper;
import ru.murzoid.project.server.vacuum.dbtool.tables.LabWorkTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.UsersTable;
import ru.murzoid.project.shared.LoginException;
import ru.murzoid.project.shared.LoginResponce;
import ru.murzoid.project.shared.TablesEnum;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LogonServiceImpl extends RemoteServiceServlet implements
		LogonService {

	private static final String ADMIN = "admin";
//	private static final String passDefault = "admin";
	public static Logger log = LogManager.getLogger(LogonServiceImpl.class);

	public LoginResponce login(String name, String pass) throws LoginException {
		log.info("User "+name+" want login to server");
		if(pass==null || pass.equals("")){
			throw new LoginException("Пустой пароль");
		}
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		// Escape data from the client to avoid cross-site script vulnerabilities.
		userAgent = escapeHtml(userAgent);
		String response="Здравствуйте"; 
		LoginResponce loginR=new LoginResponce();
		List<UsersTable> users=LibHelper.getList(TablesEnum.Users);
		if(users.size()<=0 && name.equalsIgnoreCase(ADMIN)){
			Map<String, Serializable> map=new HashMap<String, Serializable>();
			map.put("id",1);
			map.put("name",ADMIN);
			map.put("login",ADMIN);
			map.put("password",pass);//ServerUtil.getMD5(passDefault));
			map.put("gruppa","Administrators");
			map.put("permission",ADMIN);
			List<Map<String, Serializable>> list=new ArrayList<Map<String,Serializable>>();
			list.add(map);
			LibHelper.save(TablesEnum.Users, list);
			response += ", "+ADMIN + "! Это первая попытка логина к серверу. <br>Ваш логин:"+ADMIN;
			//+"<br>Ваш пароль:"+passDefault;
			loginR.setResponse(response);
			loginR.setAdmin(true);
			loginR.setSuccess(true);
			loginR.setSessionID(ActiveUsers.getSession(ADMIN));
			return loginR;
		}
		boolean dubl=false;
		for(UsersTable user: users){
			if(user.getLogin().equalsIgnoreCase(name)){
				if(user.getPassword().equals(pass)){
					if(ActiveUsers.contains(name)){
						log.info("User with login "+name+" already logged on the server");
						response += ", "+user.getName() + "! Такой пользователь уже вошел в систему.";
						dubl=true;
						break;
					}
					loginR.setSuccess(true);
					ActiveUsers.add(name);
					loginR.setSessionID(ActiveUsers.getSession(name));
					List<LabWorkTable> labWT=LibHelper.getList(TablesEnum.LabWork);
					for(LabWorkTable labT: labWT){
						if(labT.getIdUser()==user.getId()){
							loginR.setTestPass(labT.isTestPass());
						}
					}
					if(user.getPermission().equalsIgnoreCase(ADMIN)){
						log.info("User "+name+" is logged on the server with admin rights");
						loginR.setAdmin(true);
						response += ", "+user.getName() + "!<br><br>Информация о сервере: " + serverInfo
						+ ".<br><br>Информация о вас:<br>" + userAgent + "<br> Вы вошли с правами администратора.";
						break;
					}
					log.info("User "+name+" is logged on the server");
					response += ", "+user.getName() + "!<br><br>Вы используете:<br>" + userAgent + "<br> Вы успешно вошли.";
					break;
				}
				break;
			}
		}
		if(!loginR.isSuccess() && !dubl){
			response += "! Вы ввели неверно логин или пароль.";
		}
		loginR.setResponse(response);
		log.info("server response is:"+loginR);
		return loginR;
	}

	public LoginResponce relogin(String name, String session) throws LoginException {
		log.info("User "+name+" want relogin to server");
		LoginResponce loginR=new LoginResponce();
		if(name==null || name.trim().isEmpty() || session==null || session.trim().isEmpty() || 
				!ActiveUsers.contains(name) || !ActiveUsers.getSession(name).equalsIgnoreCase(session)){
			loginR.setSuccess(false);
			return loginR;
		}
		loginR.setResponse("");
		loginR.setSuccess(true);
		loginR.setSessionID(ActiveUsers.getSession(name));
		List<UsersTable> users=LibHelper.getList(TablesEnum.Users);
		List<LabWorkTable> labWT=LibHelper.getList(TablesEnum.LabWork);
		for(UsersTable user: users){
			if(user.getLogin().equalsIgnoreCase(name)){
				for(LabWorkTable labT: labWT){
					if(labT.getIdUser()==user.getId()){
						loginR.setTestPass(labT.isTestPass());
						break;
					}
				}
				if(user.getPermission().equalsIgnoreCase(ADMIN)){
					log.info("User "+name+" is relogged on the server with admin rights");
					loginR.setAdmin(true);
				}
				break;
			}
		}
		log.info("server response is:"+loginR);
		return loginR;
	}

	public String changePass(String name, String pass, String newPass) throws LoginException{
		return "";
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String logout(String name) throws LoginException {
		log.info("User "+name+" logged out from a server");
		ActiveUsers.remove(name);
		return "";
	}
}
