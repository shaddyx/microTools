package ua.org.shaddy.microtools.httptools;

public interface DataField {
	public void setContentType(String contentType);	
	public String getContentType();
	public byte[] getData();
	public String  getFileName();
	public void setFileName(String fileName);

}
