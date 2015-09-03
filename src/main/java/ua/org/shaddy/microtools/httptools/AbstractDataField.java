package ua.org.shaddy.microtools.httptools;

public abstract class AbstractDataField implements DataField {
	static String DEFAULT_CONTENT_TYPE = "application/octet-stream";
	String fileName;
	String contentType = DEFAULT_CONTENT_TYPE;
	@Override
	
	public String getContentType() {
		return contentType;
	}

	@Override
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
