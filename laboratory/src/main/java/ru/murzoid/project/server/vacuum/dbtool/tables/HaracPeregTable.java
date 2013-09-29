package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class HaracPeregTable implements InterfaceTable{
	private long id;
	private String name;
	private double soprot;
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
	public double getSoprot() {
		return soprot;
	}
	public void setSoprot(double soprot) {
		this.soprot = soprot;
	}
	@Override
	public String toString() {
		return "HaracPeregTable [id=" + id + ", name=" + name + ", soprot="
				+ soprot + "]";
	}
	
	public Map<String, Serializable> getGridData(){
		Map<String, Serializable> map=new HashMap<String, Serializable>();
		map.put("id", id);
		map.put("name", name);
		map.put("soprot", soprot);
		return map;
	}
}
