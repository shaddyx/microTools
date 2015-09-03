package ua.org.shaddy.microtools.httptools;

import java.io.UnsupportedEncodingException;

public class UrlResponse {
	private byte[] bytes;
	private String encoding = "utf-8";
	
	public UrlResponse(byte[] bytes, String encoding){
		this.bytes = bytes;
		this.encoding = encoding;
		
	}
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	/**
	 * rerturns an encoding (default is utf-8)
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}
	
	public int getLength(){
		return bytes.length;
	}
	/**
	 * sets an encoding (default is utf-8)
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String toString(){
		String out;
		try {
			out = new String(bytes, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new HttpToolsException("UrlResponse decoding error",e);
		}
		return out;
	}
}
