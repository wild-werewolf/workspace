package ru.murzoid.bookdownload.server.dto;

public class Book {

	public Book(Archive archive, String archivePath) {
		super();
		this.archive = archive;
		this.archivePath = archivePath;
		int index=archivePath.lastIndexOf(".");
		this.name = archivePath.substring(0, index);
		this.extend = archivePath.substring(index+1);
	}
	
	private Archive archive;
	private String archivePath;
	private String name;
	private String extend;
	
	public Archive getArchive() {
		return archive;
	}
	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	public String getArchivePath() {
		return archivePath;
	}
	public void setArchivePath(String archivePath) {
		this.archivePath = archivePath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
