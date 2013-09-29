package start;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import ru.murzoid.ldap.adam.install.AdamRegister;
import ru.murzoid.ldap.adam.install.exception.ADAMManagerException;


public class Starter
{
		
	public static void main(String[] args)
	{
		DOMConfigurator.configure("src/test/resources/log4j.xml");
		Logger logger = LogManager.getLogger(Starter.class);
		testAdamRegister(logger);
	}

	private static void testAdamRegister(Logger logger)
	{
		boolean flag;
		try
		{
			flag = AdamRegister.checkADAMSP1InSystem();
			logger.debug(flag);
		}
		catch (ADAMManagerException e)
		{
			logger.error(e);
		}
		logger.debug("check installation result");
		try
		{
			AdamRegister.checkADAMInstallation();
			logger.debug("installation was successfull");
		}
		catch (ADAMManagerException e)
		{
			logger.error(e);
		}
		logger.debug("check uninstallation result");
		try
		{
			AdamRegister.checkADAMUninstallation();
			logger.debug("uninstallation was successfull");
		}
		catch (ADAMManagerException e)
		{
			logger.error(e);
		}
		logger.debug("get installer");
		try
		{
			String installer=AdamRegister.getADAMInstaller();
			logger.debug(installer);
		}
		catch (ADAMManagerException e)
		{
			logger.error(e);
		}
		logger.debug("get uninstaller");
		try
		{
			String uninstaller=AdamRegister.getADAMUninstaller();
			logger.debug(uninstaller);
		}
		catch (ADAMManagerException e)
		{
			logger.error(e);
		}
	}
}
