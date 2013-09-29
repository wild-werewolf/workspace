package ru.murzoid.project.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.murzoid.project.shared.LoginException;

public class ServerUtil {
	static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2',
			(byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
			(byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c',
			(byte) 'd', (byte) 'e', (byte) 'f' };

	public static String getHexString(byte[] raw) throws UnsupportedEncodingException {
		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw) {
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}
		return new String(hex, "ASCII");
	}
	
	public static String getMD5(String text){
		try{
			byte[] defaultBytes = text.getBytes();
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();
			return getHexString(messageDigest);
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
			throw new LoginException("unknown exception occured while execute getMD5");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new LoginException("unknown exception occured while execute getMD5");
		}
	}
	public static double formulaP(int temper) {
		return (1005/(0.99534+0.000466*temper));
	}
	public static double formulaNu(int temper) {
		return ((Math.exp(Math.exp(33.22999-5.93043*Math.log(temper+273)))-0.887)/1000000);
	}
	public static double formulaMu(int temper) {
		return formulaP(temper)*formulaNu(temper);
	}
	public static double formulaGf(double gc, double woc, double xc) {
		return gc*((1-woc-xc)/(1-woc));
	}
	public static double formulaGos(double gc, double woc, double xc) {
		return gc-formulaGf(gc, woc, xc);
	}
	public static double formulaPos(int temper, double woc, double osadRot) {
		return formulaP(temper)*osadRot/(formulaP(temper)+(osadRot-formulaP(temper))*woc);
	}
	public static double formulaVfk(int temper, double gc, double woc, double xc) {
		return formulaGf(gc,woc,xc)/formulaP(temper);
	}
	public static double formulaVos(int temper, double gc, double woc, double xc, double osadRot) {
		return formulaGos(gc,woc,xc)/formulaPos(temper,woc,osadRot);
	}
	public static double formulaHos(int temper, double gc, double woc, double xc, double osadRot) {
		return 1000*formulaVos(temper,gc,woc,xc,osadRot)/0.0064;
	}
	public static double formulaA(int temper, double gc, double woc, double xc, double osadRot, double osadRoc, double deltaP) {
		return osadRoc*formulaMu(temper)*(formulaVos(temper, gc, woc, xc, osadRot)/formulaVfk(temper, gc, woc, xc))/(20*deltaP);
	}
	public static double formulaB(int temper, double peregRFP, double deltaP) {
		return peregRFP*formulaMu(temper)/(10*deltaP);
	}
	public static double formulaT(int temper, double gc, double woc, double xc, double osadRot, double osadRoc, double peregRFP, double deltaP) {
		return (formulaA(temper, gc, woc, xc, osadRot, osadRoc, deltaP)*(formulaVfk(temper, gc, woc, xc)*formulaVfk(temper, gc, woc, xc)/0.00004096)+formulaB(temper, peregRFP, deltaP)*(formulaVfk(temper, gc, woc, xc)/0.0064));
	}
	public static double formulaT(int temper, double gc, double woc, double xc, double osadRot, double osadRoc, double peregRFP, double deltaP,double value) {
		return (formulaA(temper, gc, woc, xc, osadRot, osadRoc, deltaP)*(value*value/0.00004096)+formulaB(temper, peregRFP, deltaP)*(value/0.0064));
	}
}
