package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class HaracOsadokTable implements InterfaceTable{
	private long id;
	private String name;
	private double udelsopr;
	private int ploTverFaz;
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
	public double getUdelsopr() {
		return udelsopr;
	}
	public void setUdelsopr(double udelsopr) {
		this.udelsopr = udelsopr;
	}
	public int getPloTverFaz() {
		return ploTverFaz;
	}
	public void setPloTverFaz(int ploTverFaz) {
		this.ploTverFaz = ploTverFaz;
	}
	@Override
	public String toString() {
		return "HaracOsadokTable [id=" + id + ", name=" + name + ", udelsopr="
				+ udelsopr + ", ploTverFaz=" + ploTverFaz + "]";
	}
	
	public Map<String, Serializable> getGridData(){
		Map<String, Serializable> map=new HashMap<String, Serializable>();
		map.put(new String("id"), id);
		map.put(new String("name"), name);
		map.put(new String("udelsopr"), udelsopr);
		map.put(new String("plottverfaz"), ploTverFaz);
		return map;
	}
}
