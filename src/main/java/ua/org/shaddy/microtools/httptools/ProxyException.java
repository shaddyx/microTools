package ua.org.shaddy.microtools.httptools;

public class ProxyException extends HttpToolsException {
	private static final long serialVersionUID = 8197386688938339284L;

	public ProxyException() {
		super();
	}

	public ProxyException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProxyException(String message) {
		super(message);
	}

	public ProxyException(Throwable cause) {
		super(cause);
	}
	
}
