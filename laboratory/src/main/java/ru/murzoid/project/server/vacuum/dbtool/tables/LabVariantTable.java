package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LabVariantTable implements InterfaceTable{
	private long id;
	private String name;
	private double xc;
	private double gc;
	private double woc;
	private int temper;
	private double deltap;
	private long id_harac_osad;
	private long id_harac_per;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getXc() {
		return xc;
	}
	public void setXc(double xc) {
		this.xc = xc;
	}
	public double getGc() {
		return gc;
	}
	public void setGc(double gc) {
		this.gc = gc;
	}
	public double getWoc() {
		return woc;
	}
	public void setWoc(double woc) {
		this.woc = woc;
	}
	public int getTemper() {
		return temper;
	}
	public void setTemper(int temper) {
		this.temper = temper;
	}
	public double getDeltap() {
		return deltap;
	}
	public void setDeltap(double deltap) {
		this.deltap = deltap;
	}
	public long getId_harac_as() {
		return id_harac_osad;
	}
	public void setId_harac_osad(long id_harac_osad) {
		this.id_harac_osad = id_harac_osad;
	}
	public long getId_harac_per() {
		return id_harac_per;
	}
	public void setId_harac_per(long id_harac_per) {
		this.id_harac_per = id_harac_per;
	}
	@Override
	public String toString() {
		return "LabVariantTable [id=" + id + ", name=" + name + ", xc=" + xc + ", gc=" + gc
				+ ", woc=" + woc
				+ ", temper=" + temper + ", deltap=" + deltap
				+ ", id_harac_as=" + id_harac_osad + ", id_harac_per="
				+ id_harac_per + "]";
	}
	
	public Map<String, Serializable> getGridData(){
		Map<String, Serializable> map=new HashMap<String, Serializable>();
		map.put("id",id);
		map.put("name",name);
		map.put("GC",gc);
		map.put("XC",xc);
		map.put("WOC",woc);
		map.put("temper",temper);
		map.put("deltap",deltap);
		map.put("id_harac_osadok",id_harac_osad);
		map.put("id_harac_pereg",id_harac_per);
		return map;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId_harac_osad() {
		return id_harac_osad;
	}
}
