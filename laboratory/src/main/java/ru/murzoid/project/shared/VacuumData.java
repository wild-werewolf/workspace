package ru.murzoid.project.shared;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class VacuumData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7973627095748699180L;
	//for experiments
	private List<Map<String, Serializable>> peregLT;
	private List<Map<String, Serializable>> osadokLT;
	private boolean empty=true;
	//for lab research
	private String osadok;
	private String peregorodka;
	private double A;
	private double B;
	private double H;
	private double xc;
	private double gc;
	private double woc;
	private int temper;
	private double deltap;
	private double Vf;
	private double timef;
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
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	public String getOsadok() {
		return osadok;
	}
	public void setOsadok(String osadok) {
		this.osadok = osadok;
	}
	public String getPeregorodka() {
		return peregorodka;
	}
	public void setPeregorodka(String peregorodka) {
		this.peregorodka = peregorodka;
	}
	public double getA() {
		return A;
	}
	public void setA(double a) {
		A = a;
	}
	public double getB() {
		return B;
	}
	public void setB(double b) {
		B = b;
	}
	public double getH() {
		return H;
	}
	public void setH(double h) {
		H = h;
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
	public List<Map<String, Serializable>> getPeregLT() {
		return peregLT;
	}
	public void setPeregLT(List<Map<String, Serializable>> peregLT) {
		this.peregLT = peregLT;
	}
	public List<Map<String, Serializable>> getOsadokLT() {
		return osadokLT;
	}
	public void setOsadokLT(List<Map<String, Serializable>> osadokLT) {
		this.osadokLT = osadokLT;
	}
	public double getVf() {
		return Vf;
	}
	public void setVf(double vf) {
		Vf = vf;
	}
	public double getTimef() {
		return timef;
	}
	public void setTimef(double timef) {
		this.timef = timef;
	}
	@Override
	public String toString() {
		return "VacuumData [peregLT=" + peregLT + ", osadokLT=" + osadokLT
				+ ", empty=" + empty + ", osadok=" + osadok + ", peregorodka="
				+ peregorodka + ", A=" + A + ", B=" + B + ", H=" + H + ", xc="
				+ xc + ", gc=" + gc + ", woc=" + woc + ", temper=" + temper
				+ ", deltap=" + deltap + ", Vf=" + Vf + ", timef=" + timef
				+ ", time1=" + time1 + ", time2=" + time2 + ", time3=" + time3
				+ ", time4=" + time4 + ", time5=" + time5 + ", time6=" + time6
				+ ", time7=" + time7 + ", time8=" + time8 + ", time9=" + time9
				+ ", time10=" + time10 + ", time11=" + time11 + ", time12="
				+ time12 + ", time13=" + time13 + ", time14=" + time14 + "]";
	}
}
