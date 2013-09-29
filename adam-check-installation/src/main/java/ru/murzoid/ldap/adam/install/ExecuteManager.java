package ru.murzoid.ldap.adam.install;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.ldap.adam.install.dto.CMD;
import ru.murzoid.ldap.adam.install.dto.Response;
import ru.murzoid.ldap.adam.install.exception.ADAMManagerException;

public class ExecuteManager {

	Logger logger = LogManager.getLogger(ExecuteManager.class);
	  
	public void execute(CMD command, long timeoutMinutes) throws ADAMManagerException {
		logger.info("Execute command {" + command.getNameCMD() + "}...");
		try{
			Process process = Runtime.getRuntime().exec(command.getTextCMD());
			new Thread(new NativeCommandLogger(process.getInputStream())).start();
			new Thread(new NativeCommandLogger(process.getErrorStream())).start();
			process.waitFor();
		}
		catch(Exception e){
			ADAMManagerException ex=new ADAMManagerException("Execution command "+command.getNameCMD()+" was failed due to the reason: ", e);
			logger.error(ex.getMessage(), e);
			throw ex;
		}
		logger.info("Execute command {" + command.getNameCMD() + "} was successful");
	}
		
	class NativeCommandLogger implements Runnable {
		private BufferedReader br;
		
		public NativeCommandLogger(InputStream is){
			this.br = new BufferedReader(new InputStreamReader(is));
		}

		public void run(){
			try{
				String line = null;
				while((line = br.readLine()) != null){
					if(line.trim().equals("")) {
						continue;
					} 
					Response.add(line);
				}
				br.close();
			}
			catch(Exception e){
				logger.error("Error during native process logging: ", e);
			}
		}
	}
}
