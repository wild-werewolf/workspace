package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LabWorkTable implements InterfaceTable{
	private long id;
	private long idUser;
	private long idLabVar;
	private double constA;
	private double constB;
	private double hos;
	private int time1;
	private int time2;
	private int time3;
	private int time4;
	private int time5;
	private int time6;
	private int time7;
	private int time8;
	private int time9;
	private int time10;
	private int time11;
	private int time12;
	private int time13;
	private int time14;
	private boolean testPass=false;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public long getIdLabVar() {
		return idLabVar;
	}
	public void setIdLabVar(long idLabVar) {
		this.idLabVar = idLabVar;
	}
	public double getConstA() {
		return constA;
	}
	public void setConstA(double constA) {
		this.constA = constA;
	}
	public double getConstB() {
		return constB;
	}
	public void setConstB(double constB) {
		this.constB = constB;
	}
	public int getTime1() {
		return time1;
	}
	public void setTime1(int time1) {
		this.time1 = time1;
	}
	public int getTime2() {
		return time2;
	}
	public void setTime2(int time2) {
		this.time2 = time2;
	}
	public int getTime3() {
		return time3;
	}
	public void setTime3(int time3) {
		this.time3 = time3;
	}
	public int getTime4() {
		return time4;
	}
	public void setTime4(int time4) {
		this.time4 = time4;
	}
	public int getTime5() {
		return time5;
	}
	public void setTime5(int time5) {
		this.time5 = time5;
	}
	public int getTime6() {
		return time6;
	}
	public void setTime6(int time6) {
		this.time6 = time6;
	}
	public int getTime7() {
		return time7;
	}
	public void setTime7(int time7) {
		this.time7 = time7;
	}
	public int getTime8() {
		return time8;
	}
	public void setTime8(int time8) {
		this.time8 = time8;
	}
	public int getTime9() {
		return time9;
	}
	public void setTime9(int time9) {
		this.time9 = time9;
	}
	public int getTime10() {
		return time10;
	}
	public void setTime10(int time10) {
		this.time10 = time10;
	}
	public int getTime11() {
		return time11;
	}
	public void setTime11(int time11) {
		this.time11 = time11;
	}
	public int getTime12() {
		return time12;
	}
	public void setTime12(int time12) {
		this.time12 = time12;
	}
	public int getTime13() {
		return time13;
	}
	public void setTime13(int time13) {
		this.time13 = time13;
	}
	public int getTime14() {
		return time14;
	}
	public void setTime14(int time14) {
		this.time14 = time14;
	}
	public boolean isTestPass() {
		return testPass;
	}
	public void setTestPass(boolean testPass) {
		this.testPass = testPass;
	}
	@Override
	public String toString() {
		return "LabWorkTable [id=" + id + ", idUser=" + idUser + ", idLabVar="
				+ idLabVar + ", constA=" + constA + ", constB=" + constB
				+ ", time1=" + time1 + ", time2=" + time2 + ", time3=" + time3
				+ ", time4=" + time4 + ", time5=" + time5 + ", time6=" + time6
				+ ", time7=" + time7 + ", time8=" + time8 + ", time9=" + time9
				+ ", time10=" + time10 + ", time11=" + time11 + ", time12="
				+ time12 + ", time13=" + time13 + ", time14=" + time14
				+ ", testPass=" + testPass + "]";
	}
	
	public Map<String, Serializable> getGridData(){
		Map<String, Serializable> map=new HashMap<String, Serializable>();
		map.put("id",id);
		map.put("idUser",idUser);
		map.put("idLabVariant",idLabVar);	
		map.put("Apol",constA);
		map.put("Bpol",constB);
		map.put("hos",hos);
		map.put("rezultTest",testPass);
		map.put("V02",time1);
		map.put("V04",time2);
		map.put("V06",time3);
		map.put("V08",time4);
		map.put("V10",time5);
		map.put("V12",time6);
		map.put("V14",time7);
		map.put("V16",time8);
		map.put("V18",time9);
		map.put("V20",time10);
		map.put("V22",time11);
		map.put("V24",time12);
		map.put("V26",time13);
		map.put("V28",time14);
		System.out.println(map);
		return map;
	}
	public double getHos() {
		return hos;
	}
	public void setHos(double hos) {
		this.hos = hos;
	}
}
