package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UsersTable implements InterfaceTable{
	private long id;
	private String name;
	private String login;
	private String password;
	private String gruppa;
	private String permission;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGruppa() {
		return gruppa;
	}
	public void setGruppa(String gruppa) {
		this.gruppa = gruppa;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	@Override
	public String toString() {
		return "UsersTable [name=" + name + ", login=" + login
				+ ", password=" + password + ", gruppa=" + gruppa
				+ ", permission=" + permission + "]";
	}
	
	public Map<String, Serializable> getGridData(){
		Map<String, Serializable> map=new HashMap<String, Serializable>();
		map.put("id",id);
		map.put("name",name);
		map.put("login",login);
		map.put("password",password);
		map.put("gruppa",gruppa);
		map.put("permission",permission);
		return map;
	}
}
