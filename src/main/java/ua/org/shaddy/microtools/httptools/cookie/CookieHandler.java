package ua.org.shaddy.microtools.httptools.cookie;

import java.io.IOException;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.org.shaddy.microtools.ArrayTools;
import ua.org.shaddy.microtools.ObjectPrinter;
import ua.org.shaddy.microtools.httptools.HttpToolsException;

public class CookieHandler {
	//private HashMap cookieMap = new HashMap<String,List<HttpCookie>>();
	CookieManager cookieManager = new CookieManager();
	public void updateCookies(URL url, Map<String, List<String>> responseHeaders){
		try {
			cookieManager.put(url.toURI(), responseHeaders);
		} catch (IOException e) {
			throw new HttpToolsException("Error on cookie storing: " + url,e);
		} catch (URISyntaxException e) {
			throw new HttpToolsException("Url syntax error on cookie storing: " + url,e);
		}
	}
	
	public Map<String, List<String>> getCookies(URL url){
		try {
			return cookieManager.get(url.toURI(), new HashMap<String, List<String>>());
		} catch (IOException e) {
			throw new HttpToolsException("Error on cookie storing: " + url,e);
		} catch (URISyntaxException e) {
			throw new HttpToolsException("Url syntax error on cookie storing: " + url,e);
		}
	}
	
	public String getCookieString(URL url){
		Map<String, List<String>> cookieMap = getCookies(url);
		List<String> cookies = cookieMap.get("Cookie");
		return ArrayTools.join(cookies,";");
	}
	
	public void updateCookies(String url, Map<String, List<String>> cookieHeader){
		URL aURL;
		try {
			aURL = new URL(url);
		} catch (MalformedURLException e) {
			throw new HttpToolsException("Error parsing url: " + url, e);
		}
		updateCookies(aURL,cookieHeader);
	}
	
	public void saveToFile(String file){
		//TODO: not implemented stuff for saving cookie store
		//cookieManager.getCookieStore().
	}
}
