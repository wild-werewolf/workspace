package ru.murzoid.project.server;

import java.util.ArrayList;
import java.util.List;

public class ActiveUsers {
	private static List<String> activeUsers=new ArrayList<String>();
	private static List<String> sessionID=new ArrayList<String>();

	public static List<String> getActiveUsers() {
		return activeUsers;
	}

	public static void setActiveUsers(List<String> activeUsers) {
		ActiveUsers.activeUsers = activeUsers;
	}

	public static void remove(String name) {
		for(int i=0;i<activeUsers.size();i++){
			if(activeUsers.get(i).equalsIgnoreCase(name)){
				sessionID.remove(i);
				activeUsers.remove(i);
				return;
			}
		}
	}

	public static void add(String input) {
		activeUsers.add(input);
		sessionID.add(input+"."+System.currentTimeMillis());
	}

	public static boolean contains(String input) {
		for(int i=0;i<activeUsers.size();i++){
			if(activeUsers.get(i).equalsIgnoreCase(input)){
				return true;
			}
		}
		return false;
	}
	
	public static String getSession(String input) {
		String session="";
		for(int i=0;i<activeUsers.size();i++){
			if(activeUsers.get(i).equalsIgnoreCase(input)){
				session=sessionID.get(i);
			}
		}
		return session;
	}
}
