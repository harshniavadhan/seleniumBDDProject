package com.identity.VO;

import java.nio.file.Path;

public class FileMetadataVO {
	 private String fileName = "";
     private long fileLength =0L;
     private String fileType= "";
     private String fileExtension = "";
     private Path filePath = null;
	
   
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public Path getFilePath() {
		return filePath;
	}
	public void setFilePath(Path path) {
		this.filePath = path;
	}

}
