package ua.org.shaddy.microtools.httptools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import ua.org.shaddy.microtools.ByteBuilder;
import ua.org.shaddy.microtools.httptools.cookie.CookieHandler;

public class SimpleUrl extends AbstractUrl {
	URL serverAddress = null;
	URLConnection connection = null;
	byte buffer[] = null;
	int bufferSize = 4096;
	CookieHandler cookiehandler = new CookieHandler();

	public SimpleUrl() {
		super();
	}

	@Override
	public UrlResponse get(String url) {
		init(url, "GET");
		processHeaders(url);
		return readToEnd();
	}

	@Override
	public UrlResponse post(String url, Map<String, Object> postParams) {
		init(url, "POST");
		DataOutputStream wr;
		try {
			wr = new DataOutputStream(connection.getOutputStream());
		} catch (IOException e) {
			throw new HttpToolsException("Error opening output stream for posting to " + url, e);
		}
		if (!multipart) {
			StringBuilder params = new StringBuilder();
			boolean first = true;
			for (String paramK : postParams.keySet()) {
				if (!first) {
					params.append("&");
				}
				params.append(paramK + "=" + postParams.get(paramK));
				first = false;
			}
			try {
				// System.out.println("writing:" + params.toString());
				wr.writeBytes(params.toString());
			} catch (IOException e) {
				throw new HttpToolsException("Error writing to output stream for posting to " + url, e);
			}
		} else {
			// TODO: unimplemented multipart post
			MIMEMultipart mpData = new MIMEMultipart();
			for (String key : postParams.keySet()) {
				Object value = postParams.get(key);
				if (value instanceof File) {
					try {
						mpData.putBinaryFileParam(key, new PostFile((File) value));
					} catch (Exception e) {
						throw new HttpToolsException("Error putting binary file " + url, e);
					}
				} else if (value instanceof DataField) {
					mpData.putDataField(key, (DataField) value);
				} else {
					mpData.putStandardParam(key, value.toString(), encoding);
				}

			}
		}

		try {
			wr.flush();
			wr.close();
		} catch (IOException e) {
			throw new HttpToolsException("Error posting to output stream to " + url, e);
		}
		processHeaders(url);
		return readToEnd();
	}

	private void init(String url, String method) {

		try {
			serverAddress = new URL(url);
		} catch (MalformedURLException e) {
			throw new HttpToolsException("Error creating server address for " + url, e);
		}
		try {
			connection = serverAddress.openConnection();
		} catch (IOException e) {
			throw new HttpToolsException("Error opening connection to " + url, e);
		}
		String cookieString = cookiehandler.getCookieString(serverAddress);
		connection.setRequestProperty("Cookie", cookieString);
		try {
			((HttpURLConnection) connection).setRequestMethod(method);
		} catch (ProtocolException e) {
			throw new HttpToolsException("Error setting request method GET for " + url, e);
		}
		connection.setDoOutput(true);
		connection.setReadTimeout(10000);

		try {
			connection.connect();
		} catch (IOException e) {
			throw new HttpToolsException("Error connecting to " + url, e);
		}

	}

	private void processHeaders(String url) {
		if (!isForceEncoding()) {
			String enc = connection.getContentEncoding();
			if (enc != null) {
				setEncoding(enc);
			}
		}
		cookiehandler.updateCookies(url, connection.getHeaderFields());
	}

	private UrlResponse readToEnd() {
		if (buffer == null) {
			buffer = new byte[bufferSize];
		}
		InputStream rd;
		try {
			rd = connection.getInputStream();
		} catch (IOException e) {
			throw new HttpToolsException("Error opening stream", e);
		}
		ByteBuilder bb = new ByteBuilder(bufferSize);
		int len = 0;
		try {
			while ((len = rd.read(buffer)) != -1) {
				bb.append(buffer, 0, len);
			}
			rd.close();
		} catch (IOException e) {
			throw new HttpToolsException("Error receiving data", e);
		}
		return new UrlResponse(bb.toByteArray(), getEncoding());
	}

}
