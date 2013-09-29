package ru.murzoid.bookdownload.server.dto;

import java.io.File;

public class Archive {
	
	private File file;
	
	public Archive(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public String getArchiveType(){
		if(file.isFile() && file.getName().contains(".")){
			return file.getName().substring(file.getName().lastIndexOf(".")+1);
		}
		return "undefined";
	}
	
}
