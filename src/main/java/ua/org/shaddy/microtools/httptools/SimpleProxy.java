package ua.org.shaddy.microtools.httptools;


public class SimpleProxy {
	public enum Type {	
		 HTTP
		,SOCKS4
		,SOCKS5 
	}
	private String host = "";
	private int port = 0;
	private Type type; 
	private String user = null;
	private String password = null;
	
	public SimpleProxy(String host,int port,Type type){
		this.host = host;
		this.port = port;
		this.type = type;
	}
	
	public SimpleProxy(String proxy,Type type){
		String[] proxyChunks = proxy.split("\\:");
		
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
