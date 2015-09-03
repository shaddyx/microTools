package ua.org.shaddy.microtools.httptools;

import java.io.File;
import java.io.FileInputStream;

import ua.org.shaddy.microtools.ByteBuilder;

public class MIMEMultipart {
	ByteBuilder text;
	static String CRLF = "\r\n";
	static String DEFAULT_CONTENT_TYPE = "application/octet-stream";
	String boundary;

	public MIMEMultipart() {
		text = new ByteBuilder();
		boundary = Long.toHexString(System.currentTimeMillis());
	}

	public byte[] getContent() {
		return text.toByteArray();
	}

	public String getBoundary() {
		return boundary;
	}

	public int getLength() {
		return text.size();
	}

	/**
	 * function appends String param to multipart data
	 * 
	 * @param name
	 * @param value
	 * @param encoding
	 */
	public void putStandardParam(String name, String value, String encoding) {
		StringBuilder sb = new StringBuilder();
		sb.append("--" + boundary).append(CRLF);
		sb.append("Content-Disposition: form-data; " + "name=\"" + name + "\"");
		sb.append(CRLF);
		sb.append("Content-Type: text/plain; charset=" + encoding);
		sb.append(CRLF);
		sb.append(CRLF);
		sb.append(value);
		sb.append(CRLF);
		text.append(sb.toString());
	}

	/**
	 * function adds multipart file data to mime body
	 * 
	 * @param name
	 * @param fileName
	 * @param mimeType
	 * @param encoding
	 * @throws Exception
	 */
	public void putBinaryFileParam(String name, PostFile postFile)
			throws Exception {
		// compose the header
		StringBuilder sb = new StringBuilder();
		sb.append("--" + boundary);
		sb.append(CRLF);
		sb.append("content-disposition: form-data; " + "name=\"");
		sb.append(name);
		sb.append("\";  filename=\"");
		sb.append(postFile.getName());
		sb.append("\"");
		sb.append(CRLF);
		sb.append("Content-Type: " + postFile.getContentType());
		sb.append(CRLF);
		sb.append("Content-Transfer-Encoding: binary");
		sb.append(CRLF); // need two of these
		sb.append(CRLF);
		text.append(sb.toString());
		// now for the file
		File input = new File(postFile.getPath());
		FileInputStream fis = new FileInputStream(input);
		byte[] data = new byte[(int) input.length()];
		fis.read(data);
		fis.close();
		text.append(data);
		text.append(CRLF);
	}
	
	public void putBinaryDataParam(String name, byte[] data) {
		putBinaryDataParam(name, name,  DEFAULT_CONTENT_TYPE,  data);
	}
	
	
	public void putBinaryDataParam(String name, String fileName, String contentType, byte[] data) {
		//	compose the header
		StringBuilder sb = new StringBuilder();
		sb.append("--" + boundary);
		sb.append(CRLF);
		sb.append("content-disposition: form-data; " + "name=\"");
		sb.append(name);
		sb.append("\";  filename=\"");
		sb.append(fileName);
		sb.append("\"");
		sb.append(CRLF);
		sb.append("Content-Type: " + contentType);
		sb.append(CRLF);
		sb.append("Content-Transfer-Encoding: binary");
		sb.append(CRLF); // need two of these
		sb.append(CRLF);
		text.append(sb.toString());
		// now for the file
		text.append(data);
		text.append(CRLF);
	}
	
	
	public void finish() {
		text.append("--");
		text.append(boundary);
		text.append("--");
		text.append(CRLF);
	}

	public void putDataField(String name, DataField value) {
		putBinaryDataParam(name, value.getFileName(), value.getContentType(), value.getData());
	}
}
