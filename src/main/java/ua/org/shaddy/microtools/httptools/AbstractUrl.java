package ua.org.shaddy.microtools.httptools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class AbstractUrl implements Url {

	protected int lastStatus = 0;
	protected String contentEncoding = "UTF-8";
	protected boolean multipart = false;
	protected String encoding = "utf-8";
	protected SimpleProxy proxy = null;
	protected boolean forceEncoding = false;
	public AbstractUrl() {
		super();
	}

	public SimpleProxy getProxy() {
		return proxy;
	}

	public void setProxy(SimpleProxy proxy) {
		this.proxy = proxy;
	}

	@Override
	public boolean isMultipart() {
		return multipart;
	}

	@Override
	public void setMultipart(boolean multipart) {
		this.multipart = multipart;
	}

	@Override
	public String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new HttpToolsException("Error encoding string" + url, e);
		}
	}

	@Override
	public String getEncoding() {
		return encoding;
	}

	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * returns is encoding set by setEncoding is forsed
	 * 
	 * @return
	 */
	public boolean isForceEncoding() {
		return forceEncoding;
	}

	/**
	 * sets encoding forsing policy, if true - connecctor encoding will be
	 * forsed by setEncoding
	 * 
	 * @param forceEncoding
	 */
	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}
}