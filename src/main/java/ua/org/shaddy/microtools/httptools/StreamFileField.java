package ua.org.shaddy.microtools.httptools;

import java.io.IOException;
import java.io.InputStream;

import ua.org.shaddy.microtools.ByteBuilder;

public class StreamFileField extends AbstractDataField{
	private final InputStream stream;
	
	public StreamFileField(InputStream stream, String fileName,
			String contentType) {
		setContentType(contentType);
		setFileName(fileName);
		this.stream = stream;
	}

	public StreamFileField(InputStream stream, String filename) {
		this(stream, filename, "application/octet-stream");
	}

	@Override
	public byte[] getData() {
		ByteBuilder bb = new ByteBuilder();
		byte[] buffer = new byte[1024];
		try {
			int len;
			while ((len = stream.read(buffer)) != -1){
				bb.append(buffer, 0, len);
			}
		} catch (IOException e) {
			throw new SimpleUrlException("Error while reading stream", e);
		}
		return bb.toByteArray();
	}
}
