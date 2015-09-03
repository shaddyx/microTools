package ua.org.shaddy.microtools.httptools;

public class ByteFileField extends AbstractDataField{
	
	private byte[] data;
	
	public ByteFileField(String fileName, String contentType, byte[] data) {
		this(fileName, data);
		setContentType(contentType);
		
	}
	
	public ByteFileField(String fileName, byte[] data) {
		setFileName(fileName);
		this.data = data;
	}
	
	@Override
	public byte[] getData() {
		return data;
	}
	
}
