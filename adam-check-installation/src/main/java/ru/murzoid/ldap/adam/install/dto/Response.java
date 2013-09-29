package ru.murzoid.ldap.adam.install.dto;

import java.util.ArrayList;
import java.util.List;

public class Response
{
	private static List<String> response=new ArrayList<String>();
	
	public static void clear() {
		response.clear();
	}
	
	public static void add(String str) {
		response.add(str);
	}

	public static List<String> getResponse()
	{
		return response;
	}
}
