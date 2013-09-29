package ru.murzoid.ldap.adam.install;

import java.util.List;

import ru.murzoid.ldap.adam.install.dto.CMD;
import ru.murzoid.ldap.adam.install.dto.Response;
import ru.murzoid.ldap.adam.install.exception.ADAMManagerException;


public class AdamRegister
{

	private static final String REGQUERY_UTIL = "reg query ";
	private static final String GET_VALUE_TOKEN = " /v ";
	
	private static final String ADAM_INSTALL_RESULT_KEY="HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\ADAM_Installer_Results";
	private static final String ADAM_INSTALL_ER_CODE_KEY="ADAMInstallErrorCode";
	private static final String ADAM_INSTALL_ER_MSG_KEY="ADAMInstallErrorMessage";
	private static final String ADAM_INSTALL_WARN_KEY="ADAMInstallWarnings";
	private static final String ADAM_UNINSTALL_ER_CODE_KEY="ADAMUninstallErrorCode";
	private static final String ADAM_UNINSTALL_ER_MSG_KEY="ADAMUninstallErrorMessage";
	private static final String ADAM_UNINSTALL_WARN_KEY="ADAMUninstallWarnings";
	private static final String ADAM_SP1_KEY="HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ADAM_SP\\SP1";
	private static final String ADAM_SP1_INSTALL_KEY="Installed";
	private static final String ADAM_KEY="HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ADAM_Shared";
	private static final String ADAM_INSTALL_KEY="VersionMajor";
	private static final String ADAM_INSTALLER_KEY="InstanceInstaller";
	private static final String ADAM_UNINSTALLER_KEY="InstanceUninstaller";
	private static final String ERROR_MSG = "UNABLE TO FIND THE SPECIFIED REGISTRY KEY OR VALUE";
	private static final String ERROR = "ERROR:";
	private static final String DEFAULT_INSTALLER_PATH = "%windir%\\adam\\adaminstall.exe";
	private static final String DEFAULT_UNINSTALLER_PATH = "%windir%\\adam\\adamuninstall.exe";
	
	static public boolean checkADAMSP1InSystem() throws ADAMManagerException {
		String tmp;
		Response.clear();
		String cmd=REGQUERY_UTIL+ADAM_KEY+GET_VALUE_TOKEN+ADAM_INSTALL_KEY;
		execCmd(cmd, "get ADAM key", 0);
		tmp=parse(Response.getResponse(),ADAM_INSTALL_KEY);
		if(tmp==null || !tmp.equalsIgnoreCase("0x1")) {
			return false;
		}
		Response.clear();
		cmd=REGQUERY_UTIL+ADAM_SP1_KEY+GET_VALUE_TOKEN+ADAM_SP1_INSTALL_KEY;
		execCmd(cmd, "get ADAM key", 0);
		tmp=parse(Response.getResponse(),ADAM_SP1_INSTALL_KEY);
		if(tmp==null || !tmp.equalsIgnoreCase("0x1")) {
			return false;
		}
		Response.clear();
		return true;
	}

	static public void checkADAMInstallation() throws ADAMManagerException {
		String cmd="";
		String errorMsg="";
		String tmp;
		Response.clear();
		cmd=REGQUERY_UTIL+ADAM_INSTALL_RESULT_KEY+GET_VALUE_TOKEN+ADAM_INSTALL_ER_CODE_KEY;
		execCmd(cmd, "get ADAM Result Installation error code", 0);
		tmp=parse(Response.getResponse(),ADAM_INSTALL_ER_CODE_KEY);
		if(tmp!=null) {
			errorMsg+=tmp;
		}
		Response.clear();
		cmd=REGQUERY_UTIL+ADAM_INSTALL_RESULT_KEY+GET_VALUE_TOKEN+ADAM_INSTALL_ER_MSG_KEY;
		execCmd(cmd, "get ADAM Result Installation error message", 0);
		tmp=parse(Response.getResponse(),ADAM_INSTALL_ER_MSG_KEY);
		if(tmp!=null) {
			errorMsg+=tmp;
		}
		Response.clear();
		cmd=REGQUERY_UTIL+ADAM_INSTALL_RESULT_KEY+GET_VALUE_TOKEN+ADAM_INSTALL_WARN_KEY;
		execCmd(cmd, "get ADAM Result Installation warning", 0);
		tmp=parse(Response.getResponse(),ADAM_INSTALL_WARN_KEY);
		if(tmp!=null) {
			errorMsg+=tmp;
		}
		Response.clear();
		if(errorMsg!=null && !errorMsg.trim().equals("")) {
			throw new ADAMManagerException(errorMsg);
		}
	}

	static public void checkADAMUninstallation() throws ADAMManagerException {
		String cmd="";
		String errorMsg="";
		String tmp;
		Response.clear();
		cmd=REGQUERY_UTIL+ADAM_INSTALL_RESULT_KEY+GET_VALUE_TOKEN+ADAM_UNINSTALL_ER_CODE_KEY;
		execCmd(cmd, "get ADAM Result Installation error code", 0);
		tmp=parse(Response.getResponse(),ADAM_UNINSTALL_ER_CODE_KEY);
		if(tmp!=null) {
			errorMsg+=tmp;
		}
		Response.clear();
		cmd=REGQUERY_UTIL+ADAM_INSTALL_RESULT_KEY+GET_VALUE_TOKEN+ADAM_UNINSTALL_ER_MSG_KEY;
		execCmd(cmd, "get ADAM Result Installation error message", 0);
		tmp=parse(Response.getResponse(),ADAM_UNINSTALL_ER_MSG_KEY);
		if(tmp!=null) {
			errorMsg+=tmp;
		}
		Response.clear();
		cmd=REGQUERY_UTIL+ADAM_INSTALL_RESULT_KEY+GET_VALUE_TOKEN+ADAM_UNINSTALL_WARN_KEY;
		execCmd(cmd, "get ADAM Result Installation warning", 0);
		tmp=parse(Response.getResponse(),ADAM_UNINSTALL_WARN_KEY);
		if(tmp!=null) {
			errorMsg+=tmp;
		}
		Response.clear();
		if(errorMsg!=null && !errorMsg.trim().equals("")) {
			throw new ADAMManagerException(errorMsg);
		}
	}
	
	static public String getADAMInstaller() throws ADAMManagerException {
		String tmp;
		Response.clear();
		String cmd=REGQUERY_UTIL+ADAM_KEY+GET_VALUE_TOKEN+ADAM_INSTALLER_KEY;
		execCmd(cmd, "get ADAM Result Installation error code", 0);
		tmp=parse(Response.getResponse(),ADAM_INSTALLER_KEY);
		if(tmp!=null && !tmp.trim().equals("")) {
			return tmp;
		}
		Response.clear();
		return DEFAULT_INSTALLER_PATH;
	}
	
	static public String getADAMUninstaller() throws ADAMManagerException {
		String tmp;
		Response.clear();
		String cmd=REGQUERY_UTIL+ADAM_KEY+GET_VALUE_TOKEN+ADAM_UNINSTALLER_KEY;
		execCmd(cmd, "get ADAM Result Installation error code", 0);
		tmp=parse(Response.getResponse(),ADAM_UNINSTALLER_KEY);
		if(tmp!=null && !tmp.trim().equals("")) {
			return tmp;
		}
		Response.clear();
		return DEFAULT_UNINSTALLER_PATH;
	}
	
	private static String parse(List<String> response, String adamInstallKey) throws ADAMManagerException
	{
		String value=null;
		for(String str: response) {
			if(str.trim().toUpperCase().startsWith(ERROR)) {
				if(str.toUpperCase().contains(ERROR_MSG)) {
					return value;
				} 
				throw new ADAMManagerException(str);
			} else if(str.trim().startsWith(adamInstallKey)) {
				String[] values=str.trim().split("\t");
				value=values[values.length-1];
			}
		}
		return value;
	}



	static public void execCmd(String cmd, String subCmdName, int time) throws ADAMManagerException{
		ExecuteManager exeManager = new ExecuteManager();
		CMD command=new CMD();
		command.setNameCMD(subCmdName);
		command.setTextCMD(cmd);
		if(time<0 || time>30){
			time=0;
		}
		command.setMsgWaitCMD("Wait " + time+ " minutes to execute comand");
		command.setLogLevelInfo(true);
		exeManager.execute(command, time);
	}
}
