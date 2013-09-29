package ru.murzoid.ldap.adam.install.dto;

public class CMD {
	
	private String nameCMD;
	private String textCMD;
	private String msgWaitCMD;
	private boolean logLevelInfo;
	
	public String getNameCMD() {
		return nameCMD;
	}
	public void setNameCMD(String nameCMD) {
		this.nameCMD = nameCMD;
	}
	public String getTextCMD() {
		return textCMD;
	}
	public void setTextCMD(String textCMD) {
		this.textCMD = textCMD;
	}
	public String getMsgWaitCMD() {
		return msgWaitCMD;
	}
	public void setMsgWaitCMD(String msgWaitCMD) {
		this.msgWaitCMD = msgWaitCMD;
	}
	public boolean isLogLevelInfo() {
		return logLevelInfo;
	}
	public void setLogLevelInfo(boolean logLevelInfo) {
		this.logLevelInfo = logLevelInfo;
	}
}
