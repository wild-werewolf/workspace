package ru.murzoid.bookdownload.server.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.bookdownload.server.exceptions.BookAnalyzeException;

public class ZipHelper {

	public static Logger log = LogManager.getLogger(ZipHelper.class);
	private List<String> successedFiles;
	
	public List<String> analyze(File arhive) throws BookAnalyzeException{
		log.info("Start analyze arhive: " + arhive.getAbsolutePath());
			ZipFile zipFile = null;
			List<String> list=new ArrayList<String>();
			try {
				
				zipFile = new ZipFile(arhive);
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				while (entries.hasMoreElements()) {
					try {
						ZipEntry zipEntry = entries.nextElement();
						list.add(zipEntry.getName());
					} catch (IllegalArgumentException e){
						log.error("unknown exception", e);
					}
				}
			} catch (IOException e) {
				throw new BookAnalyzeException("Error reading ZIP file "+arhive.getName(), e);
			}finally {
				safeClose(zipFile);
			}
		log.info("End analyze arhive: " + arhive.getAbsolutePath());
		return list;
	}

	private void safeClose(ZipFile zipFile) {
		if (zipFile != null) {
			try {
				zipFile.close();
			} catch (IOException e) {
				// error
			}
		}
	}
	
	public List<String> moveFiles(List<String> files, String toPath) {
		log.info("Move files to folder: " + toPath);
		for (String fileToMove : files) {
			try{
				unzip(fileToMove, toPath, "");
				successedFiles.add(fileToMove);
			} catch (IOException e) {
				log.info("Failed to move file " + fileToMove + " from virtual file system to " + toPath + ": , due error: " + e);
			}
		}
		log.info("Move files ... OK");
		return successedFiles;
	}

	private String unzip(String fileName, String toPath, String arhive) throws IOException {
		File fileTo = null;
		ZipFile zip = null;
		String fileToName = this.processDestFilePath(fileName);
		
		fileTo = new File(toPath + File.separator + fileToName);
		if (fileTo.exists()) {
			fileTo.delete();
		}else{
			if(!fileTo.getParentFile().exists()){
				fileTo.getParentFile().mkdirs();
			}
			fileTo.createNewFile();	
		}
		zip = new ZipFile(arhive);
		ZipEntry entry = new ZipEntry(fileName.replace("\\", "/"));
		write(zip.getInputStream(entry), new BufferedOutputStream(
				new FileOutputStream(fileTo)));

		zip.close();
		return fileToName;
	}

	public void write(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
	
	public void createZip(Set<File> listFiles, String target){
	    
	    // Create a buffer for reading the files
	    byte[] buf = new byte[1024];
	    
	    try {
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target));
	    
	        // Compress the files
	        for (File file: listFiles) {
	            FileInputStream in = new FileInputStream(file);
	    
	            // Add ZIP entry to output stream.
	            out.putNextEntry(new ZipEntry(file.getName()));
	    
	            // Transfer bytes from the file to the ZIP file
	            int len;
	            while ((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	    
	            // Complete the entry
	            out.closeEntry();
	            in.close();
	        }
	    
	        // Complete the ZIP file
	        out.close();
	    } catch (IOException e) {
	    }
	}

	public String processDestFilePath(String filePath){
		return filePath.split("\\\\", 2)[1];
	}
	
	public static void main(String [] args) throws UnsupportedEncodingException{
		String [] str={};
		Set<File> files=new HashSet<File>();
		for(String name:str){
			files.add(new File(name));
		}
//		byte [] tmp={65, 46, 95, 65, 46, 95, 84, 117, 84, -83, 114, 105, 110, 95, 82, 117, 103, 97, 116, 101, 108, 115, 116, 118, 97, 95, 110, 97, 95, 49, 53, 95, 121, 97, 122, 121, 105, 107, 97, 104, 46, 112, 100, 102};
//		Charset cs=new MS1251();
//		String str1=new String(tmp, cs);
//		System.out.println(str1);
//		System.out.println(new String(str1.getBytes("Cp1251")));
		ZipHelper zip=new ZipHelper();
		try {
			zip.analyze(new File("e:\\books\\lib.rus.ec\\usr\\usr-172703-182284.zip"));
		} catch (BookAnalyzeException e) {
			e.printStackTrace();
		}
	}
}
