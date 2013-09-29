package ru.murzoid.bookdownload.server;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BookDelete implements Runnable
{

	public static Logger log = LogManager.getLogger(BookDelete.class);
	private static final long ONE_HOUR_TIMEOUT = 3600000;
	public Thread th;
	private File file;
	
	
	public BookDelete(File file)
	{
		if(file==null) return;
		this.file=file;
		th = new Thread(this);
		start();
	}

	public void start()
	{
		th.start();
	}

	public void run()
	{
		log.info("start comand delete file "+file.getName()+", timeout "+ONE_HOUR_TIMEOUT/60000+" minutes");
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
		if(file==null){
			return;
		}
		try {
			Thread.sleep(ONE_HOUR_TIMEOUT);
		} catch (InterruptedException e) {
			log.error("problem with wait command", e);
		}
		try {
			deleteFileAndDir(file);
		} catch (Exception e){
			log.error("error occured wile deleted file "+file.getName(), e);
		}
		log.info("file "+file.getName()+" deleted success");
	}

	public boolean deleteFileAndDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteFileAndDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}