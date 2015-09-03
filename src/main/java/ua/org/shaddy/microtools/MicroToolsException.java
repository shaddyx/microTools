package ua.org.shaddy.microtools;

public class MicroToolsException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 8622713655675883908L;

    public MicroToolsException() {
        super();
    }

    public MicroToolsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MicroToolsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroToolsException(String message) {
        super(message);
    }

    public MicroToolsException(Throwable cause) {
        super(cause);
    }

}
