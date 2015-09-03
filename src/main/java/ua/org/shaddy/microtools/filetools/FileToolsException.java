package ua.org.shaddy.microtools.filetools;

public class FileToolsException extends RuntimeException {

	/**
     * 
     */
    private static final long serialVersionUID = -1760194144809806776L;

    public FileToolsException() {
	}

	public FileToolsException(String message) {
		super(message);
	}

	public FileToolsException(Throwable cause) {
		super(cause);
	}

	public FileToolsException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileToolsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
