package ua.org.shaddy.microtools.httptools;

import java.util.Map;

public interface Url {

	public boolean isMultipart();

	/**
	 * sets request multipart mode
	 * @param multipart
	 */
	public void setMultipart(boolean multipart);
	
	/**
	 * encodes url to current charset
	 * @param url
	 * @return
	 */
	public String urlEncode(String url);

	/**
	 * Makes the get request
	 * @param url - url to get
	 * @return
	 */
	public UrlResponse get(String url);
	/**
	 * Posts the data to url
	 * @param url - URL to post
	 * @param hashMap - data to post, can contain Strings, numeric, File, but also supports Object (by calling) .toString method
	 * @return	
	 */
	public UrlResponse post(String url,Map<String, Object> hashMap);
	/**
	 * sets the proxy for connection
	 * @param simpleProxy
	 */
	public void setProxy(SimpleProxy simpleProxy);
	
	/**
	 * sets the encoding
	 * @param encoding
	 * @return
	 */
	public void setEncoding(String encoding);
	
	/**
	 * gets the encoding
	 * @return
	 */
	public String getEncoding();
	/**
	 * returns is encoding set by setEncoding is forsed
	 * 
	 * @return
	 */
	public boolean isForceEncoding();
	/**
	 * sets encoding forsing policy, if true - connecctor encoding will be
	 * forsed by setEncoding
	 * 
	 * @param forceEncoding
	 */
	public void setForceEncoding(boolean forceEncoding);
	
}